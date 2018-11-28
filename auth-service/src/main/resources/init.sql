-- users

create table if not exists users (
  id char(36) not null constraint users_pkey primary key,
  username varchar(100) not null,
  password varchar(100) not null,
  created_at timestamp not null
);

create unique index users_username_uindex on users (username);

-- roles

create table if not exists roles (
  id serial not null constraint roles_pkey primary key,
  name varchar(255) not null
);

create unique index if not exists roles_name_uindex on roles (name);

insert into roles (name) values ('user'), ('driver');

-- role_user

create table if not exists role_user (
  role_id integer not null constraint role_user_roles_id_fk references roles on delete cascade,
  user_id char(36) not null constraint role_user_users_id_fk references users on delete cascade,
  constraint role_user_pk primary key (role_id, user_id)
);

create index if not exists role_user_role_id_index on role_user (role_id);
create index if not exists role_user_user_id_index on role_user (user_id);
