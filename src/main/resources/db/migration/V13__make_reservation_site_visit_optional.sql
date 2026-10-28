ALTER TABLE tbl_reservation
    MODIFY COLUMN visit_site_date DATE NULL,
    MODIFY COLUMN visit_site_time TIME(6) NULL,
    MODIFY COLUMN visit_site_exit_time TIME(6) NULL;
