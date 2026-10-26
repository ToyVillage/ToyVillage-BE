-- 엔티티에는 있지만 마이그레이션에 빠져 있던 created_at 컬럼을 추가한다.
-- 이미 컬럼이 있는 DB에서는 아무것도 하지 않는다.

SET @sql := IF(
    (SELECT COUNT(*) FROM information_schema.columns
     WHERE table_schema = DATABASE() AND table_name = 'tbl_animal_kind' AND column_name = 'created_at') = 0,
    'ALTER TABLE tbl_animal_kind ADD COLUMN created_at DATETIME(6) NULL',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql := IF(
    (SELECT COUNT(*) FROM information_schema.columns
     WHERE table_schema = DATABASE() AND table_name = 'tbl_work_report' AND column_name = 'created_at') = 0,
    'ALTER TABLE tbl_work_report ADD COLUMN created_at DATETIME(6) NULL',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
