insert into users(username, role, name)
values ('user', 'USER', 'John');
insert into users(username, role, name)
values ('admin', 'ADMIN', 'John');

insert into teacher (name, id)
values ('Victor', 5);
insert into teacher (name, id)
values ('Ionut', 6);

insert into training (description, name, start_date, teacher_id, id)
values ('All about Spring', 'Spring Framework', '23-May-20', 5, 1);
insert into training (description, name, start_date, teacher_id, id)
values ('The coolest standard in Java EE', 'JPA', '15-May-20', 5, 2);
insert into training (description, name, start_date, teacher_id, id)
values ('The new way of doing Single Page Applications', 'Java Basic', '02-Jun-20', 6, 3);
insert into training (description, name, start_date, teacher_id, id)
values ('Design Thinking', 'DesignPatterns', '15-May-20', 6, 4);

