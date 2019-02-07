CREATE TABLE IF NOT EXISTS users (
  id varchar(36) PRIMARY KEY,
  username varchar(100) NOT NULL UNIQUE,
  password varchar(72) NOT NULL,
  role varchar(50) NOT NULL,
  created_at timestamp NOT NULL DEFAULT current_timestamp
);