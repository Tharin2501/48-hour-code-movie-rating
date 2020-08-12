drop table if exists user_roles;
drop table if exists users;
create table users (email varchar(255) not null, enabled boolean not null, last_name varchar(55), name varchar(55), password varchar(340), primary key (email));
create table user_roles (user_email varchar(255) not null, roles varchar(255));


