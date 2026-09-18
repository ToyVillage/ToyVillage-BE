alter table tbl_notice_team drop foreign key fk_notice_team_team;
alter table tbl_notice_team modify column team_id bigint null;
alter table tbl_notice_team
    add constraint fk_notice_team_team foreign key (team_id)
    references tbl_team (id) on delete set null;
