ALTER TABLE tbl_work_log MODIFY COLUMN write_at DATETIME(6) NOT NULL;
ALTER TABLE tbl_work_log ADD COLUMN write_time_known BIT(1) NOT NULL DEFAULT b'0';
