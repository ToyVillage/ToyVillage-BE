create table tbl_notice_team (
    id bigint not null auto_increment,
    notice_id bigint not null,
    team_id bigint not null,
    primary key (id),
    constraint uk_notice_team unique (notice_id, team_id),
    constraint fk_notice_team_notice foreign key (notice_id) references tbl_notice (id),
    constraint fk_notice_team_team foreign key (team_id) references tbl_team (id)
);

alter table tbl_notice drop column kind;
