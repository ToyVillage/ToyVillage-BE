ALTER TABLE tbl_feed_log
    ADD COLUMN feed_unit ENUM ('GML', 'KGL') NOT NULL DEFAULT 'GML';
