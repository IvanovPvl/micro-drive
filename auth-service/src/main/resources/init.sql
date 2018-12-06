-- users

CREATE TABLE IF NOT EXISTS users (
  id CHAR(36) NOT NULL CONSTRAINT users_pkey PRIMARY KEY,
  username VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  created_at TIMESTAMP NOT NULL
);

CREATE UNIQUE index users_username_uindex ON users (username);

-- roles

CREATE TABLE IF NOT EXISTS roles (
  id serial NOT NULL CONSTRAINT roles_pkey PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS roles_name_uindex ON roles (name);

INSERT INTO roles (name) VALUES ('user'), ('driver');

-- role_user

CREATE TABLE IF NOT EXISTS role_user (
  role_id INTEGER NOT NULL CONSTRAINT role_user_roles_id_fk REFERENCES roles ON DELETE CASCADE,
  user_id CHAR(36) NOT NULL CONSTRAINT role_user_users_id_fk REFERENCES users ON DELETE CASCADE,
  CONSTRAINT role_user_pk PRIMARY KEY (role_id, user_id)
);

CREATE INDEX IF NOT EXISTS role_user_role_id_index ON role_user (role_id);
CREATE INDEX IF NOT EXISTS role_user_user_id_index ON role_user (user_id);

-- oauth_client_details

CREATE TABLE IF NOT EXISTS oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256) NOT NULL,
  scope VARCHAR(256) NOT NULL,
  authorized_grant_types VARCHAR(256) NOT NULL,
  redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  auto_approve VARCHAR(256)
);
