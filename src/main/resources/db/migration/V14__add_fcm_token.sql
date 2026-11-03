CREATE TABLE tbl_fcm_token (
    token VARCHAR(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL PRIMARY KEY,
    app_admin_id BIGINT NOT NULL,
    INDEX idx_fcm_token_app_admin (app_admin_id),
    CONSTRAINT fk_fcm_token_app_admin FOREIGN KEY (app_admin_id) REFERENCES tbl_app_admin (app_admin_id)
);
