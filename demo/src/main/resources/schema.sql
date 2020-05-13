create table users
(
    username varchar2(50) primary key,
    role     varchar2(20),
    name     varchar2(30) not NULL
);