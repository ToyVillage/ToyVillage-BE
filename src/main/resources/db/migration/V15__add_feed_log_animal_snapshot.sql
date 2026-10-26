ALTER TABLE tbl_feed_log
    ADD COLUMN animal_name      VARCHAR(255)                                  NULL,
    ADD COLUMN animal_kind_name VARCHAR(255)                                  NULL,
    ADD COLUMN animal_taxonomic ENUM ('BIRDS', 'FISH', 'MAMMALS', 'REPTILES') NULL,
    ADD COLUMN animal_deleted   BIT(1)                                        NOT NULL DEFAULT b'0';

UPDATE tbl_feed_log f
    JOIN tbl_animal_manage a ON a.animal_manage_id = f.animal_manage_id
    JOIN tbl_animal_kind k ON k.id = a.animal_kind_id
SET f.animal_name      = a.animal_name,
    f.animal_kind_name = k.kind_name,
    f.animal_taxonomic = k.animal_taxonomic;

ALTER TABLE tbl_feed_log
    MODIFY animal_name      VARCHAR(255)                                  NOT NULL,
    MODIFY animal_kind_name VARCHAR(255)                                  NOT NULL,
    MODIFY animal_taxonomic ENUM ('BIRDS', 'FISH', 'MAMMALS', 'REPTILES') NOT NULL,
    MODIFY animal_manage_id BIGINT                                        NULL;
