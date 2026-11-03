CREATE TABLE tbl_task_assignee (
    task_id      BIGINT NOT NULL,
    app_admin_id BIGINT NOT NULL,
    PRIMARY KEY (task_id, app_admin_id),
    CONSTRAINT fk_task_assignee_task      FOREIGN KEY (task_id)      REFERENCES tbl_task (task_id),
    CONSTRAINT fk_task_assignee_app_admin FOREIGN KEY (app_admin_id) REFERENCES tbl_app_admin (app_admin_id)
);

INSERT INTO tbl_task_assignee (task_id, app_admin_id)
SELECT t.task_id, t.assignee_id
FROM tbl_task t
WHERE t.assignee_type = 'EMPLOYEE' AND t.assignee_id IS NOT NULL;

INSERT INTO tbl_task_assignee (task_id, app_admin_id)
SELECT t.task_id, j.app_admin_id
FROM tbl_task t
JOIN tbl_join_team j ON j.team_id = t.assignee_team_id
JOIN tbl_app_admin a ON a.app_admin_id = j.app_admin_id AND a.role = 'EMPLOYEE'
WHERE t.assignee_type = 'TEAM' AND t.assignee_team_id IS NOT NULL;

INSERT INTO tbl_task_assignee (task_id, app_admin_id)
SELECT t.task_id, a.app_admin_id
FROM tbl_task t
JOIN tbl_app_admin a ON a.role = 'EMPLOYEE'
WHERE t.assignee_type = 'ALL';

SELECT t.task_id, t.task_title, t.assignee_type, t.assignee_id, t.assignee_team_id
FROM tbl_task t
LEFT JOIN tbl_task_assignee ta ON ta.task_id = t.task_id
WHERE ta.task_id IS NULL;

ALTER TABLE tbl_work_report ADD COLUMN app_admin_id BIGINT NULL;

UPDATE tbl_work_report SET app_admin_id = app_admin_app_admin_id;

UPDATE tbl_work_report w
SET w.app_admin_id = (SELECT MIN(ta.app_admin_id) FROM tbl_task_assignee ta WHERE ta.task_id = w.task_id)
WHERE w.app_admin_id IS NULL;

DELETE FROM tbl_work_report_file
WHERE work_report_id IN (SELECT work_report_id FROM tbl_work_report WHERE app_admin_id IS NULL);

DELETE FROM tbl_work_report WHERE app_admin_id IS NULL;

ALTER TABLE tbl_work_report MODIFY COLUMN app_admin_id BIGINT NOT NULL;

ALTER TABLE tbl_work_report ADD CONSTRAINT uk_work_report_task_app_admin UNIQUE (task_id, app_admin_id);

ALTER TABLE tbl_work_report DROP INDEX UKnja66vt3skfbjahw5njwu0nel;

ALTER TABLE tbl_work_report DROP FOREIGN KEY FK4lil00089kf7lm8ln5w8dp88q;

ALTER TABLE tbl_work_report DROP INDEX UKa1ddx24sg791pk4h1kw6o9v3u;

ALTER TABLE tbl_work_report DROP COLUMN app_admin_app_admin_id;

ALTER TABLE tbl_work_report ADD CONSTRAINT fk_work_report_app_admin FOREIGN KEY (app_admin_id) REFERENCES tbl_app_admin (app_admin_id);

ALTER TABLE tbl_task DROP FOREIGN KEY FKa6scuoi7cx3immrqs4cg93a5;

ALTER TABLE tbl_task DROP FOREIGN KEY FKa4j8q17put04ilcdh00ne39bk;

ALTER TABLE tbl_task DROP COLUMN assignee_type;

ALTER TABLE tbl_task DROP COLUMN assignee_id;

ALTER TABLE tbl_task DROP COLUMN assignee_team_id;

ALTER TABLE tbl_task MODIFY COLUMN task_content TEXT;

ALTER TABLE tbl_work_report MODIFY COLUMN content VARCHAR(2000) NOT NULL;

ALTER TABLE tbl_work_report MODIFY COLUMN note VARCHAR(2000) NULL;

ALTER TABLE tbl_app_admin ADD COLUMN position VARCHAR(30) NULL;
