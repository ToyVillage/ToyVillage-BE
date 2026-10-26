ALTER TABLE tbl_animal_manage
    ADD COLUMN delete_yn BIT(1) NOT NULL DEFAULT b'0';

ALTER TABLE tbl_animal_kind
    ADD COLUMN delete_yn BIT(1) NOT NULL DEFAULT b'0';
