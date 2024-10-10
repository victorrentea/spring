drop table if exists person;

create table person (
    id int not null auto_increment,
    name varchar(255) not null,
    primary key (id)
);

insert into person (name) values ('Marko');
insert into person (name) values ('Nour');