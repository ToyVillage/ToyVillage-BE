ALTER TABLE tbl_work_log_template
    ADD COLUMN original_template_id BIGINT NULL,
    ADD INDEX idx_work_log_template_original (original_template_id),
    DROP INDEX UKdapyaeekgxj1l6u586q9d1myt,
    ADD COLUMN active_template_title VARCHAR(50)
        GENERATED ALWAYS AS (CASE WHEN delete_yn = b'0' THEN template_title ELSE NULL END) VIRTUAL,
    ADD UNIQUE INDEX uk_work_log_template_active_title (active_template_title);
