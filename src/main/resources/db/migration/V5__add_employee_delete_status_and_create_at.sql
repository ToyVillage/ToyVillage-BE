SET @employee_schema_sql = IF(
    (SELECT COUNT(*) FROM information_schema.columns
     WHERE table_schema = DATABASE() AND table_name = 'tbl_app_admin' AND column_name = 'delete_status') = 0,
    'ALTER TABLE tbl_app_admin ADD COLUMN delete_status BIT(1) NOT NULL DEFAULT b''0''',
    'SELECT 1'
);
PREPARE employee_schema_statement FROM @employee_schema_sql;
EXECUTE employee_schema_statement;
DEALLOCATE PREPARE employee_schema_statement;

SET @employee_schema_sql = IF(
    (SELECT COUNT(*) FROM information_schema.columns
     WHERE table_schema = DATABASE() AND table_name = 'tbl_app_admin' AND column_name = 'create_at') = 0,
    'ALTER TABLE tbl_app_admin ADD COLUMN create_at DATE NULL',
    'SELECT 1'
);
PREPARE employee_schema_statement FROM @employee_schema_sql;
EXECUTE employee_schema_statement;
DEALLOCATE PREPARE employee_schema_statement;

UPDATE tbl_app_admin SET delete_status = b'0' WHERE delete_status IS NULL;

ALTER TABLE tbl_app_admin
    MODIFY COLUMN delete_status BIT(1) NOT NULL DEFAULT b'0',
    MODIFY COLUMN create_at DATE NULL;

UPDATE tbl_app_admin SET create_at = NULL WHERE CAST(create_at AS CHAR) = '0000-00-00';
