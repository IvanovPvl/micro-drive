CREATE TABLE IF NOT EXISTS cars (
  id SERIAL PRIMARY KEY,
  mark VARCHAR(50),
  number VARCHAR(50),
  user_id VARCHAR(36) REFERENCES users(id),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_user_id ON cars (user_id);
