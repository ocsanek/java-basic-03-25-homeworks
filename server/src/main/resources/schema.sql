CREATE TABLE IF NOT EXISTS users (
  id IDENTITY PRIMARY KEY,
  login VARCHAR(64) UNIQUE NOT NULL,
  pass_hash VARCHAR(255) NOT NULL,
  username VARCHAR(64) UNIQUE NOT NULL,
  role VARCHAR(16) NOT NULL DEFAULT 'user', -- 'admin' | 'user'
  banned_until TIMESTAMP NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_activity TIMESTAMP NULL
);

CREATE TABLE IF NOT EXISTS rooms (
  id IDENTITY PRIMARY KEY,
  name VARCHAR(64) UNIQUE NOT NULL,
  pass VARCHAR(64),
  owner VARCHAR(64) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_active TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS messages (
  id IDENTITY PRIMARY KEY,
  sender VARCHAR(64) NOT NULL,
  room VARCHAR(64) NOT NULL DEFAULT 'general',
  is_private BOOLEAN NOT NULL DEFAULT FALSE,
  recipient VARCHAR(64),
  body VARCHAR(1000) NOT NULL,
  ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ratings (
  id IDENTITY PRIMARY KEY,
  rater VARCHAR(64) NOT NULL,
  rated VARCHAR(64) NOT NULL,
  delta INT NOT NULL,
  ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- начальные пользователи
MERGE INTO users (login, pass_hash, username, role) KEY(login)
VALUES ('admin','{PLAIN}admin','admin','admin'),
       ('qwe','{PLAIN}qwe','qwe1','user'),
       ('asd','{PLAIN}asd','asd1','user'),
       ('zxc','{PLAIN}zxc','zxc1','user');