
drop table if exists movie;
drop table if exists movie_ranks;
drop table if exists rank;
drop table if exists users cascade;
drop table if exists user_roles;
create table movie (id bigint not null, genre varchar(255), plot varchar(355), title varchar(55),director varchar(255),year_of_release integer (255), primary key (id));
create table movie_ranks (movie_id bigint not null, ranks_movie_id bigint not null, ranks_user_email varchar(255) not null);
create table rank (rating integer check (rating<=5 AND rating>=1), movie_id bigint not null, user_email varchar(255) not null, primary key (movie_id, user_email));
create table users (email varchar(255) not null, enabled boolean not null, last_name varchar(55), name varchar(55), password varchar(340), primary key (email));
create table user_roles (user_email varchar(255) not null, roles varchar(255));
create table review (id bigint not null, content varchar(255), title varchar(255), rank_movie_id bigint, rank_user_email varchar(255), primary key (id));