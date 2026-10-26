CREATE TABLE tbl_fifth_floor_feed_log
(
    fifth_floor_feed_log_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    feed_date               DATE                 NOT NULL,
    feed_type               VARCHAR(255)         NOT NULL,
    feed_amount             FLOAT                NOT NULL,
    feed_unit               ENUM ('GML', 'KGL') NOT NULL,
    animal_manage_id        BIGINT               NOT NULL,
    writer_id               BIGINT               NULL,
    CONSTRAINT fk_fifth_floor_feed_log_animal_manage
        FOREIGN KEY (animal_manage_id) REFERENCES tbl_animal_manage (animal_manage_id),
    CONSTRAINT fk_fifth_floor_feed_log_writer
        FOREIGN KEY (writer_id) REFERENCES tbl_app_admin (app_admin_id)
);
