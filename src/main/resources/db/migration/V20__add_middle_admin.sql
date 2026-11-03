ALTER TABLE tbl_app_admin MODIFY COLUMN role ENUM ('APP_ADMIN', 'MIDDLE_ADMIN', 'EMPLOYEE') NOT NULL;

ALTER TABLE tbl_task ADD COLUMN creator_id BIGINT NULL;
ALTER TABLE tbl_task
    ADD CONSTRAINT fk_task_creator FOREIGN KEY (creator_id) REFERENCES tbl_app_admin (app_admin_id);
