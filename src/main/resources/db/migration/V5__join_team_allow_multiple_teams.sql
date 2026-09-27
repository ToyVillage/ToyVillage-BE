alter table tbl_join_team
    add constraint uk_join_team_app_admin_team unique (app_admin_id, team_id);

alter table tbl_join_team
    drop index uk_join_team_app_admin;
