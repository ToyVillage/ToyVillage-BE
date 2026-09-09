-- #124 업무지시 담당자 다중화 마이그레이션 (MySQL 8)
--
-- 배포 순서: 애플리케이션 중지 -> 이 스크립트 실행 -> 신규 버전 기동
-- ddl-auto: update 는 컬럼/인덱스 삭제와 타입 축소를 하지 않으므로 수동 실행이 필요하다.
-- Hibernate 가 만든 제약 이름은 DB 마다 다르므로 information_schema 로 찾아 처리한다.

-- ---------------------------------------------------------------------------
-- 1. 담당자 다중화용 조인 테이블
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tbl_task_assignee (
    task_id      BIGINT NOT NULL,
    app_admin_id BIGINT NOT NULL,
    PRIMARY KEY (task_id, app_admin_id),
    CONSTRAINT fk_task_assignee_task      FOREIGN KEY (task_id)      REFERENCES tbl_task (task_id),
    CONSTRAINT fk_task_assignee_app_admin FOREIGN KEY (app_admin_id) REFERENCES tbl_app_admin (app_admin_id)
);

-- ---------------------------------------------------------------------------
-- 2. 기존 담당자 데이터 이관 (EMPLOYEE / TEAM / ALL)
-- ---------------------------------------------------------------------------
INSERT IGNORE INTO tbl_task_assignee (task_id, app_admin_id)
SELECT t.task_id, t.assignee_id
FROM tbl_task t
WHERE t.assignee_type = 'EMPLOYEE' AND t.assignee_id IS NOT NULL;

INSERT IGNORE INTO tbl_task_assignee (task_id, app_admin_id)
SELECT t.task_id, j.app_admin_id
FROM tbl_task t
JOIN tbl_join_team j ON j.team_id = t.assignee_team_id
JOIN tbl_app_admin a ON a.app_admin_id = j.app_admin_id AND a.role = 'EMPLOYEE'
WHERE t.assignee_type = 'TEAM' AND t.assignee_team_id IS NOT NULL;

INSERT IGNORE INTO tbl_task_assignee (task_id, app_admin_id)
SELECT t.task_id, a.app_admin_id
FROM tbl_task t
JOIN tbl_app_admin a ON a.role = 'EMPLOYEE'
WHERE t.assignee_type = 'ALL';

-- ---------------------------------------------------------------------------
-- 3. 업무보고: 업무지시당 1건 -> 담당자별 1건
-- ---------------------------------------------------------------------------
-- 3-1. 작성자 컬럼 신설 (없을 때만)
SET @sql = (SELECT IF(COUNT(*) = 0,
    'ALTER TABLE tbl_work_report ADD COLUMN app_admin_id BIGINT NULL',
    'DO 0')
    FROM information_schema.columns
    WHERE table_schema = DATABASE() AND table_name = 'tbl_work_report' AND column_name = 'app_admin_id');
PREPARE st FROM @sql; EXECUTE st; DEALLOCATE PREPARE st;

-- 3-2. 구 컬럼(app_admin_app_admin_id)에서 작성자 이관
SET @sql = (SELECT IF(COUNT(*) = 1,
    'UPDATE tbl_work_report SET app_admin_id = app_admin_app_admin_id WHERE app_admin_id IS NULL OR app_admin_id = 0',
    'DO 0')
    FROM information_schema.columns
    WHERE table_schema = DATABASE() AND table_name = 'tbl_work_report' AND column_name = 'app_admin_app_admin_id');
PREPARE st FROM @sql; EXECUTE st; DEALLOCATE PREPARE st;

-- 3-3. 작성자를 알 수 없으면 해당 업무지시의 담당자 중 가장 작은 id 로 귀속
UPDATE tbl_work_report w
SET w.app_admin_id = (SELECT MIN(ta.app_admin_id) FROM tbl_task_assignee ta WHERE ta.task_id = w.task_id)
WHERE w.app_admin_id IS NULL OR w.app_admin_id = 0;

-- 3-4. 그래도 귀속 불가한 고아 보고 제거 (담당자가 한 명도 없는 업무지시)
DELETE FROM tbl_work_report_file
WHERE work_report_id IN (SELECT work_report_id FROM tbl_work_report WHERE app_admin_id IS NULL OR app_admin_id = 0);
DELETE FROM tbl_work_report WHERE app_admin_id IS NULL OR app_admin_id = 0;

ALTER TABLE tbl_work_report MODIFY COLUMN app_admin_id BIGINT NOT NULL;

-- 3-5. 담당자별 1건 제약을 먼저 만든다 (task_id 선행 컬럼이라 기존 외래키 인덱스를 대체한다)
SET @sql = (SELECT IF(COUNT(*) = 0,
    'ALTER TABLE tbl_work_report ADD CONSTRAINT uk_work_report_task_app_admin UNIQUE (task_id, app_admin_id)',
    'DO 0')
    FROM information_schema.statistics
    WHERE table_schema = DATABASE() AND table_name = 'tbl_work_report'
      AND index_name = 'uk_work_report_task_app_admin');
PREPARE st FROM @sql; EXECUTE st; DEALLOCATE PREPARE st;

-- 3-6. 업무지시당 1건만 허용하던 UNIQUE 제거
SET @idx = (SELECT index_name FROM information_schema.statistics
    WHERE table_schema = DATABASE() AND table_name = 'tbl_work_report'
      AND non_unique = 0 AND seq_in_index = 1 AND column_name = 'task_id'
      AND index_name NOT IN ('PRIMARY', 'uk_work_report_task_app_admin')
    LIMIT 1);
SET @sql = IF(@idx IS NULL, 'DO 0', CONCAT('ALTER TABLE tbl_work_report DROP INDEX `', @idx, '`'));
PREPARE st FROM @sql; EXECUTE st; DEALLOCATE PREPARE st;

-- 3-7. 구 작성자 컬럼의 외래키 -> UNIQUE -> 컬럼 순으로 제거
SET @fk = (SELECT constraint_name FROM information_schema.key_column_usage
    WHERE table_schema = DATABASE() AND table_name = 'tbl_work_report'
      AND column_name = 'app_admin_app_admin_id' AND referenced_table_name IS NOT NULL LIMIT 1);
SET @sql = IF(@fk IS NULL, 'DO 0', CONCAT('ALTER TABLE tbl_work_report DROP FOREIGN KEY `', @fk, '`'));
PREPARE st FROM @sql; EXECUTE st; DEALLOCATE PREPARE st;

SET @idx = (SELECT index_name FROM information_schema.statistics
    WHERE table_schema = DATABASE() AND table_name = 'tbl_work_report'
      AND column_name = 'app_admin_app_admin_id' AND index_name <> 'PRIMARY' LIMIT 1);
SET @sql = IF(@idx IS NULL, 'DO 0', CONCAT('ALTER TABLE tbl_work_report DROP INDEX `', @idx, '`'));
PREPARE st FROM @sql; EXECUTE st; DEALLOCATE PREPARE st;

SET @sql = (SELECT IF(COUNT(*) = 1,
    'ALTER TABLE tbl_work_report DROP COLUMN app_admin_app_admin_id',
    'DO 0')
    FROM information_schema.columns
    WHERE table_schema = DATABASE() AND table_name = 'tbl_work_report' AND column_name = 'app_admin_app_admin_id');
PREPARE st FROM @sql; EXECUTE st; DEALLOCATE PREPARE st;

-- 3-8. 신규 작성자 외래키
SET @sql = (SELECT IF(COUNT(*) = 0,
    'ALTER TABLE tbl_work_report ADD CONSTRAINT fk_work_report_app_admin FOREIGN KEY (app_admin_id) REFERENCES tbl_app_admin (app_admin_id)',
    'DO 0')
    FROM information_schema.table_constraints
    WHERE table_schema = DATABASE() AND table_name = 'tbl_work_report'
      AND constraint_name = 'fk_work_report_app_admin');
PREPARE st FROM @sql; EXECUTE st; DEALLOCATE PREPARE st;

-- ---------------------------------------------------------------------------
-- 4. 이전 빌드에서 만들어졌을 수 있는 공개 범위 컬럼 제거
--    (기능이 폐기되어 더 이상 사용하지 않는다. develop 스키마에는 원래 없다.)
-- ---------------------------------------------------------------------------
SET @sql = (SELECT IF(COUNT(*) = 1, 'ALTER TABLE tbl_task DROP COLUMN visibility', 'DO 0')
    FROM information_schema.columns
    WHERE table_schema = DATABASE() AND table_name = 'tbl_task' AND column_name = 'visibility');
PREPARE st FROM @sql; EXECUTE st; DEALLOCATE PREPARE st;

-- ---------------------------------------------------------------------------
-- 5. 단일 담당자 컬럼 제거
-- ---------------------------------------------------------------------------
SET @fk = (SELECT constraint_name FROM information_schema.key_column_usage
    WHERE table_schema = DATABASE() AND table_name = 'tbl_task'
      AND column_name = 'assignee_id' AND referenced_table_name IS NOT NULL LIMIT 1);
SET @sql = IF(@fk IS NULL, 'DO 0', CONCAT('ALTER TABLE tbl_task DROP FOREIGN KEY `', @fk, '`'));
PREPARE st FROM @sql; EXECUTE st; DEALLOCATE PREPARE st;

SET @fk = (SELECT constraint_name FROM information_schema.key_column_usage
    WHERE table_schema = DATABASE() AND table_name = 'tbl_task'
      AND column_name = 'assignee_team_id' AND referenced_table_name IS NOT NULL LIMIT 1);
SET @sql = IF(@fk IS NULL, 'DO 0', CONCAT('ALTER TABLE tbl_task DROP FOREIGN KEY `', @fk, '`'));
PREPARE st FROM @sql; EXECUTE st; DEALLOCATE PREPARE st;

SET @sql = (SELECT IF(COUNT(*) = 1, 'ALTER TABLE tbl_task DROP COLUMN assignee_type', 'DO 0')
    FROM information_schema.columns
    WHERE table_schema = DATABASE() AND table_name = 'tbl_task' AND column_name = 'assignee_type');
PREPARE st FROM @sql; EXECUTE st; DEALLOCATE PREPARE st;

SET @sql = (SELECT IF(COUNT(*) = 1, 'ALTER TABLE tbl_task DROP COLUMN assignee_id', 'DO 0')
    FROM information_schema.columns
    WHERE table_schema = DATABASE() AND table_name = 'tbl_task' AND column_name = 'assignee_id');
PREPARE st FROM @sql; EXECUTE st; DEALLOCATE PREPARE st;

SET @sql = (SELECT IF(COUNT(*) = 1, 'ALTER TABLE tbl_task DROP COLUMN assignee_team_id', 'DO 0')
    FROM information_schema.columns
    WHERE table_schema = DATABASE() AND table_name = 'tbl_task' AND column_name = 'assignee_team_id');
PREPARE st FROM @sql; EXECUTE st; DEALLOCATE PREPARE st;

-- ---------------------------------------------------------------------------
-- 6. 내용 컬럼 확장 (tinytext 는 255바이트라 한글 약 85자에서 잘린다)
-- ---------------------------------------------------------------------------
ALTER TABLE tbl_task MODIFY COLUMN task_content TEXT;
ALTER TABLE tbl_work_report MODIFY COLUMN content VARCHAR(2000) NOT NULL;
ALTER TABLE tbl_work_report MODIFY COLUMN note VARCHAR(2000) NULL;

-- ---------------------------------------------------------------------------
-- 7. 직원 직급
-- ---------------------------------------------------------------------------
SET @sql = (SELECT IF(COUNT(*) = 0, 'ALTER TABLE tbl_app_admin ADD COLUMN position VARCHAR(30) NULL', 'DO 0')
    FROM information_schema.columns
    WHERE table_schema = DATABASE() AND table_name = 'tbl_app_admin' AND column_name = 'position');
PREPARE st FROM @sql; EXECUTE st; DEALLOCATE PREPARE st;
