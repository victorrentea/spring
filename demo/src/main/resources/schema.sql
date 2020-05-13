create table users
(
    username varchar2(50) primary key,
    role     varchar2(20),
    name     varchar2(30) not NULL
);

create table teacher
(
    id   bigint not null,
    name varchar(255),
    primary key (id)
);

create table training
(
    id          bigint not null,
    description varchar(255),
    name        varchar(255),
    start_date  varchar(255),
    teacher_id  bigint,
    primary key (id)
);

alter table training
    add constraint FK_TEACHER_TRAININGS foreign key (teacher_id) references teacher;

create sequence TRAINING_SEQ start with 10 increment by 1;

