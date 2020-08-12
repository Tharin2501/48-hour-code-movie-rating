create sequence hibernate_sequence start with 1 increment by 1;
create table users (username varchar(255) not null, enabled boolean not null, password varchar(255), primary key (username));
create table user_roles (user_username varchar(255) not null, roles varchar(255));

