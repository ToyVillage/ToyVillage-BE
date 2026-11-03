create table document
(
    document_id    bigint auto_increment
        primary key,
    created_at     datetime(6)                         null,
    document_title varchar(100)                        not null,
    document_type  enum ('JPG', 'OTHER', 'PDF', 'PNG') not null
);

create table tbl_admin
(
    admin_id bigint auto_increment
        primary key,
    email    varchar(255) not null,
    password varchar(255) not null,
    constraint UKsqa0p6s7t4qt312cpk050v62q
        unique (email)
);

create table tbl_animal
(
    animal_id          bigint auto_increment
        primary key,
    animal_description varchar(255)                                  not null,
    animal_image       varchar(255)                                  not null,
    animal_kind        varchar(50)                                   not null,
    animal_type        enum ('BIRDS', 'FISH', 'MAMMALS', 'REPTILES') not null,
    popular_animal     bit                                           not null
);

create table tbl_animal_legal_status
(
    animal_legal_status_id bigint auto_increment
        primary key,
    kind                   varchar(255) not null
);

create table tbl_app_admin
(
    app_admin_id bigint auto_increment
        primary key,
    name         varchar(255)                   not null,
    password     varchar(255)                   not null,
    role         enum ('APP_ADMIN', 'EMPLOYEE') not null,
    username     varchar(255)                   not null,
    position     varchar(30)                    null,
    constraint UKbylarsfnsmtq30a8e9egm5vgd
        unique (username)
);

create table tbl_close_day
(
    id               bigint auto_increment
        primary key,
    end_close_time   date         not null,
    start_close_time date         not null,
    title            varchar(255) not null
);

create table tbl_faq
(
    question_id      bigint auto_increment
        primary key,
    question_answer  tinytext not null,
    question_content tinytext not null
);

create table tbl_file
(
    file_id   bigint auto_increment
        primary key,
    file_key  varchar(255) not null,
    file_name varchar(255) not null,
    constraint UKcb75qs47xb2d5bc51mhdmurs9
        unique (file_key)
);

create table tbl_animal_kind
(
    id               bigint auto_increment
        primary key,
    animal_taxonomic enum ('BIRDS', 'FISH', 'MAMMALS', 'REPTILES') not null,
    detail_kind      varchar(255)                                  not null,
    eng_name         varchar(255)                                  not null,
    kind_name        varchar(255)                                  not null,
    scientific_name  varchar(255)                                  not null,
    file_id          bigint                                        not null,
    constraint FKa3mn8tv9jn6k79ghxjmarrump
        foreign key (file_id) references tbl_file (file_id)
);

create table tbl_animal_legal_designation
(
    id                     bigint auto_increment
        primary key,
    animal_kind_id         bigint       not null,
    animal_legal_status_id bigint       not null,
    animal_legal_status    varchar(255) not null,
    constraint FK15qwdtrqb73894sdaeacxe9a0
        foreign key (animal_kind_id) references tbl_animal_kind (id),
    constraint FKj1khbxfhl078gbljjv0ih20vh
        foreign key (animal_legal_status_id) references tbl_animal_legal_status (animal_legal_status_id)
);

create table tbl_animal_manage
(
    animal_manage_id bigint auto_increment
        primary key,
    animal_gender    enum ('MAN', 'UNKNOWN', 'WOMAN') not null,
    animal_name      varchar(255)                     not null,
    birth_year       int                              not null,
    other_info       varchar(255)                     null,
    file_id          bigint                           not null,
    animal_kind_id   bigint                           not null,
    constraint FKbujnv6u9w8airctk1tb1o6qlt
        foreign key (animal_kind_id) references tbl_animal_kind (id),
    constraint FKq1euw57ek09x4v8lbb0e9clnk
        foreign key (file_id) references tbl_file (file_id)
);

create table tbl_animal_observation
(
    animal_observation_id bigint auto_increment
        primary key,
    content               varchar(2000) not null,
    created_at            datetime(6)   not null,
    title                 varchar(100)  not null,
    animal_manage_id      bigint        not null,
    author_id             bigint        not null,
    constraint FKcr24y48m2scqhwrsfacslk39v
        foreign key (animal_manage_id) references tbl_animal_manage (animal_manage_id),
    constraint FKg3cb92jdiirr3mvotyiw2ox11
        foreign key (author_id) references tbl_app_admin (app_admin_id)
);

create table tbl_animal_observation_file
(
    id                    bigint auto_increment
        primary key,
    animal_observation_id bigint not null,
    file_id               bigint not null,
    constraint FK9kdmb6n4hsfoj33wfiovcq7i9
        foreign key (file_id) references tbl_file (file_id),
    constraint FKo2wysaaprm8f9ktat6vcx27xh
        foreign key (animal_observation_id) references tbl_animal_observation (animal_observation_id)
);

create table tbl_document_file
(
    document_id bigint not null,
    file_id     bigint not null,
    constraint UKgengy0ydyhd0iq2mvu1561wx9
        unique (file_id),
    constraint FKdij68c8to1fdpcnx762bli5dq
        foreign key (document_id) references document (document_id),
    constraint FKt5c6wga5mddlb1sg62enob9q8
        foreign key (file_id) references tbl_file (file_id)
);

create table tbl_event
(
    event_id          bigint auto_increment
        primary key,
    event_description varchar(255) not null,
    event_end_date    datetime(6)  not null,
    event_start_date  datetime(6)  not null,
    event_subjects    varchar(255) not null,
    event_name        varchar(255) not null,
    file_id           bigint       null,
    constraint UK9jcvof7nojykc57lua42p2vyx
        unique (file_id),
    constraint FKie0alquef8l6626xf8hpm9d4x
        foreign key (file_id) references tbl_file (file_id)
);

create table tbl_feed_log
(
    feed_log_id      bigint auto_increment
        primary key,
    feed_amount      float        not null,
    feed_date_time   datetime(6)  not null,
    feed_type        varchar(255) not null,
    significant      varchar(255) not null,
    animal_manage_id bigint       not null,
    writer_id        bigint       null,
    constraint FK55o1clrsaeupmmfk9300gys8t
        foreign key (writer_id) references tbl_app_admin (app_admin_id),
    constraint FKk9y5oisysam5i56l6fxponmu
        foreign key (animal_manage_id) references tbl_animal_manage (animal_manage_id)
);

create table tbl_gallery
(
    gallery_id    bigint auto_increment
        primary key,
    gallery_title varchar(50) not null,
    file_id       bigint      not null,
    constraint UKrlyd74cb57ywpnu72jg667bc5
        unique (file_id),
    constraint FKi1riohmk4o9kdj8yle68e18dm
        foreign key (file_id) references tbl_file (file_id)
);

create table tbl_news
(
    news_id          bigint auto_increment
        primary key,
    news_postdate    datetime(6)  not null,
    news_description text         not null,
    news_title       varchar(255) not null
);

create table tbl_news_file
(
    news_id bigint not null,
    file_id bigint not null,
    constraint UKl60gcq9irdinph6p0qa6gyyv0
        unique (file_id),
    constraint FK6melb7oossb3ln9jc68vmr124
        foreign key (news_id) references tbl_news (news_id),
    constraint FKkl2pg53xnu32em6gggxsqrpxq
        foreign key (file_id) references tbl_file (file_id)
);

create table tbl_notice
(
    id         bigint auto_increment
        primary key,
    content    varchar(255) not null,
    created_at date         not null,
    kind       enum ('ALL') not null,
    title      varchar(255) not null
);

create table tbl_notice_file
(
    notice_id bigint not null,
    file_id   bigint not null,
    constraint UKeunlq7iub29cnyrw4g76w2k9h
        unique (file_id),
    constraint FKfqx7itcdh0xlb9r6xdr4wu3w5
        foreign key (notice_id) references tbl_notice (id),
    constraint FKh0qficcos35nrbxpc20mk7nem
        foreign key (file_id) references tbl_file (file_id)
);

create table tbl_open_time
(
    id              bigint auto_increment
        primary key,
    end_open_time   time(6) not null,
    start_open_time time(6) not null,
    open_date       date    not null
);

create table tbl_partnership
(
    partnership_id           bigint auto_increment
        primary key,
    partnership_content      longtext                                     not null,
    partnership_date         datetime(6)                                  not null,
    partnership_email        varchar(255)                                 not null,
    partnership_name         varchar(50)                                  not null,
    partnership_phone_number varchar(15)                                  not null,
    partnership_title        varchar(50)                                  not null,
    partnership_type         enum ('MARKETING', 'OTHER', 'STORE_OPENING') not null
);

create table tbl_partnership_file
(
    partnership_id bigint not null,
    file_id        bigint not null,
    constraint UKaeigjdj5nyklrfe07025t0k6e
        unique (file_id),
    constraint FK53298on9b4kib52pmjn5f6hhu
        foreign key (file_id) references tbl_file (file_id),
    constraint FK8im4upvfmo1ln4e9rafghmtk0
        foreign key (partnership_id) references tbl_partnership (partnership_id)
);

create table tbl_pu
(
    pu_id           bigint auto_increment
        primary key,
    expiration_date date   not null,
    priority        int    not null,
    file_id         bigint not null,
    constraint UK24o43j69bgyuo1ns28e57rgqc
        unique (file_id),
    constraint FKkno2r8xyvxkwxobcnml0nvd8a
        foreign key (file_id) references tbl_file (file_id)
);

create table tbl_reservation
(
    id                   bigint auto_increment
        primary key,
    title                varchar(255)                                                          not null,
    reservation_name     varchar(255)                                                          not null,
    leader_count         int                                                                   not null,
    reservation_count    int                                                                   not null,
    visit_site_count     int                                                                   not null,
    location             varchar(255)                                                          not null,
    visit_date           date                                                                  not null,
    out_time             time(6)                                                               not null,
    counsel_date         date                                                                  not null,
    visit_site_date      date                                                                  not null,
    visit_site_time      time(6)                                                               not null,
    visit_site_exit_time time(6)                                                               not null,
    reservation_date     date                                                                  not null,
    money                int                                                                   not null,
    leader_phone_number  varchar(15)                                                           not null,
    reservation_time     time(6)                                                               not null,
    status               enum ('BEFORE_SITE_VISIT', 'SITE_VISIT_COMPLETED', 'VISIT_COMPLETED') not null,
    visit_time           time(6)                                                               not null
);

create table tbl_reservation_permission
(
    id             bigint auto_increment
        primary key,
    reservation_id bigint not null,
    app_admin_id   bigint not null,
    constraint FK4sypcte4gqe4b55omj8nqk7gw
        foreign key (app_admin_id) references tbl_app_admin (app_admin_id),
    constraint fk_reservation_permission_reservation
        foreign key (reservation_id) references tbl_reservation (id)
);

create index idx_reservation_permission_reservation_id
    on tbl_reservation_permission (reservation_id);

create table tbl_team
(
    id        bigint auto_increment
        primary key,
    team_name varchar(255) not null
);

create table tbl_join_team
(
    join_team_id bigint auto_increment
        primary key,
    team_id      bigint not null,
    app_admin_id bigint not null,
    constraint uk_join_team_app_admin
        unique (app_admin_id),
    constraint FK4yrcqrb8obsngt6d70t0thtg6
        foreign key (app_admin_id) references tbl_app_admin (app_admin_id),
    constraint FKhp47wri07djgj57f3d1qpifa2
        foreign key (team_id) references tbl_team (id)
);

create table tbl_task
(
    task_id          bigint auto_increment
        primary key,
    assignee_type    enum ('ALL', 'EMPLOYEE', 'TEAM') not null,
    task_content     tinytext                         null,
    created_at       datetime(6)                      not null,
    finish_date      date                             not null,
    priority         enum ('HIGH', 'LOW', 'MEDIUM')   not null,
    task_title       varchar(100)                     not null,
    assignee_id      bigint                           null,
    assignee_team_id bigint                           null,
    constraint FKa4j8q17put04ilcdh00ne39bk
        foreign key (assignee_team_id) references tbl_team (id),
    constraint FKa6scuoi7cx3immrqs4cg93a5
        foreign key (assignee_id) references tbl_app_admin (app_admin_id)
);

create table tbl_task_assignee
(
    task_id      bigint not null,
    app_admin_id bigint not null,
    constraint FKd2iftnmkd06kn5vak9ybk6sar
        foreign key (app_admin_id) references tbl_app_admin (app_admin_id),
    constraint FKlyd0e3jv2nv1d36jvssday1sw
        foreign key (task_id) references tbl_task (task_id)
);

create table tbl_task_file
(
    task_id bigint not null,
    file_id bigint not null,
    constraint UKflpin9gphcqe6hk7scqqyowcj
        unique (file_id),
    constraint FK1wmjpyg1hd3078ngpyfeee4lg
        foreign key (task_id) references tbl_task (task_id),
    constraint FK301bn5hxw8ffvi3xknsk1tpk8
        foreign key (file_id) references tbl_file (file_id)
);

create table tbl_user
(
    id       bigint auto_increment
        primary key,
    name     varchar(255) not null,
    email    varchar(255) not null,
    password varchar(255) not null,
    constraint uk_tbl_user_email
        unique (email)
);

create table tbl_web_admin
(
    web_admin_id bigint auto_increment
        primary key,
    email        varchar(255) not null,
    password     varchar(255) not null,
    constraint UKr6ojxl4ngthi1px8dpegpexlp
        unique (email)
);

create table tbl_work_log_template
(
    work_log_template_id bigint auto_increment
        primary key,
    created_at           date        not null,
    template_title       varchar(50) not null,
    delete_yn            bit         not null,
    app_admin_id         bigint      not null,
    constraint UKdapyaeekgxj1l6u586q9d1myt
        unique (template_title),
    constraint FK40f5v9gjhd00itfqwy89jn3v9
        foreign key (app_admin_id) references tbl_app_admin (app_admin_id)
);

create table tbl_work_log
(
    work_log_id          bigint auto_increment
        primary key,
    write_at             date   not null,
    app_admin_id         bigint not null,
    work_log_template_id bigint not null,
    constraint FKj4yvyg5n622ovkw5h48bjeca0
        foreign key (work_log_template_id) references tbl_work_log_template (work_log_template_id),
    constraint FKscydl5tsef1hvhnum1havfbh2
        foreign key (app_admin_id) references tbl_app_admin (app_admin_id)
);

create table tbl_work_log_question
(
    work_log_question_id bigint auto_increment
        primary key,
    question             varchar(80)                                                  not null,
    question_order       int                                                          not null,
    question_type        enum ('TEXT', 'MULTIPLE_CHOICE', 'CHECK_BOX', 'FILE_UPLOAD') not null,
    work_log_template_id bigint                                                       not null,
    constraint FKe36pjqicyip26if8yjkqgiee4
        foreign key (work_log_template_id) references tbl_work_log_template (work_log_template_id)
);

create table tbl_work_log_question_option
(
    work_log_question_option_id bigint auto_increment
        primary key,
    content                     varchar(30) not null,
    etc_option                  bit         not null,
    number                      int         not null,
    work_log_question_id        bigint      not null,
    constraint FKfffal4lujjbc0f4mtilmkctbj
        foreign key (work_log_question_id) references tbl_work_log_question (work_log_question_id)
);

create table tbl_work_log_section
(
    work_log_section_id  bigint auto_increment
        primary key,
    section_name         varchar(20) not null,
    section_order        int         not null,
    work_log_template_id bigint      not null,
    constraint FK5huk0hhbjrsyq6511f3vvn4v4
        foreign key (work_log_template_id) references tbl_work_log_template (work_log_template_id)
);

create table tbl_work_log_answer
(
    work_log_answer_id   bigint auto_increment
        primary key,
    answer_text          varchar(500) null,
    file_id              bigint       null,
    work_log_question_id bigint       not null,
    work_log_section_id  bigint       not null,
    work_log_id          bigint       not null,
    constraint FKh73rav6n64p9g2hl8a5y2h40h
        foreign key (work_log_question_id) references tbl_work_log_question (work_log_question_id),
    constraint FKj4enfwi3pvqea27r1bn24sspl
        foreign key (file_id) references tbl_file (file_id),
    constraint FKla25ii4d9xx2ojgc70okxbxrj
        foreign key (work_log_section_id) references tbl_work_log_section (work_log_section_id),
    constraint FKlf2wubljhbjjrvaddqkwt5glt
        foreign key (work_log_id) references tbl_work_log (work_log_id)
);

create table tbl_work_log_answer_option
(
    work_log_answer_option_id   bigint auto_increment
        primary key,
    etc_text                    varchar(500) null,
    work_log_answer_id          bigint       not null,
    work_log_question_option_id bigint       not null,
    constraint FKa5xo8yg0c9neoff4kqee30q5m
        foreign key (work_log_question_option_id) references tbl_work_log_question_option (work_log_question_option_id),
    constraint FKq21ilkfk2qbo3uru38204v1kb
        foreign key (work_log_answer_id) references tbl_work_log_answer (work_log_answer_id)
);

create table tbl_work_report
(
    work_report_id         bigint auto_increment
        primary key,
    content                varchar(2000)                                       not null,
    note                   varchar(2000)                                       null,
    rejection_reason       varchar(1000)                                       null,
    status                 enum ('APPROVED', 'MISSING', 'PENDING', 'REJECTED') not null,
    app_admin_app_admin_id bigint                                              null,
    task_id                bigint                                              not null,
    app_admin_id           bigint                                              not null,
    constraint UKa1ddx24sg791pk4h1kw6o9v3u
        unique (app_admin_app_admin_id),
    constraint UKnja66vt3skfbjahw5njwu0nel
        unique (task_id),
    constraint uk_work_report_task_app_admin
        unique (task_id, app_admin_id),
    constraint FK4lil00089kf7lm8ln5w8dp88q
        foreign key (app_admin_app_admin_id) references tbl_app_admin (app_admin_id),
    constraint FK73ytds67j4w4rj2yp65uh9xwf
        foreign key (task_id) references tbl_task (task_id),
    constraint FKj7uuc8vmjt107va7f1byldr58
        foreign key (app_admin_id) references tbl_app_admin (app_admin_id)
);

create table tbl_work_report_file
(
    work_report_id bigint not null,
    file_id        bigint not null,
    constraint UK7c7hic418s6trq1uigsg6kw9
        unique (file_id),
    constraint FK9p0hlw1aqxk2wut8w05rsvn14
        foreign key (work_report_id) references tbl_work_report (work_report_id),
    constraint FKn7etkowmpsltn2t9siuy7vt2s
        foreign key (file_id) references tbl_file (file_id)
);