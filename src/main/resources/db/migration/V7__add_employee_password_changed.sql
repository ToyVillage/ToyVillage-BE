SET @employee_password_sql = IF(
    (SELECT COUNT(*) FROM information_schema.columns
     WHERE table_schema = DATABASE() AND table_name = 'tbl_app_admin' AND column_name = 'password_changed') = 0,
    'ALTER TABLE tbl_app_admin ADD COLUMN password_changed BIT(1) NOT NULL DEFAULT b''0''',
    'SELECT 1'
);
PREPARE employee_password_statement FROM @employee_password_sql;
EXECUTE employee_password_statement;
DEALLOCATE PREPARE employee_password_statement;

UPDATE tbl_app_admin SET password_changed = b'0' WHERE password_changed IS NULL;

ALTER TABLE tbl_app_admin
    MODIFY COLUMN password_changed BIT(1) NOT NULL DEFAULT b'0';
