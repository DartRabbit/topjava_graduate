DROP TABLE VOTES
IF EXISTS;
DROP TABLE USERS
IF EXISTS;
DROP TABLE DISHES
IF EXISTS;
DROP TABLE RESTAURANTS
IF EXISTS;

DROP SEQUENCE GLOBAL_SEQ
IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ
  AS INTEGER
  START WITH 100000;

CREATE TABLE USERS
(
  id       INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  name     VARCHAR(255) NOT NULL,
  email    VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  is_admin BOOLEAN DEFAULT FALSE
);
CREATE UNIQUE INDEX users_unique_email_idx
  ON USERS (email);

-- CREATE TABLE USER_ROLES
-- (
--   user_id INTEGER NOT NULL,
--   role    VARCHAR(255),
--   CONSTRAINT user_roles_idx UNIQUE (user_id, role),
--   FOREIGN KEY (user_id) REFERENCES USERS (id)
--     ON DELETE CASCADE
-- );

CREATE TABLE RESTAURANTS
(
  id   INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  name VARCHAR(255)
);
CREATE UNIQUE INDEX restaurant_unique_name_idx
  ON RESTAURANTS (name);

CREATE TABLE DISHES
(
  id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  date          DATE DEFAULT today,
  name          VARCHAR(255) NOT NULL,
  price         INTEGER      NOT NULL,
  restaurant_id INTEGER      NOT NULL,
  FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS (id)
    ON DELETE CASCADE
);
CREATE INDEX dishes_unique_restaurant_date_idx
  ON DISHES (restaurant_id, date);

CREATE TABLE VOTES
(
  user_id       INTEGER NOT NULL,
  restaurant_id INTEGER NOT NULL,
  date          DATE DEFAULT today,
  PRIMARY KEY (user_id, date),
  FOREIGN KEY (user_id) REFERENCES USERS (id)
    ON DELETE CASCADE,
  FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS (id)
    ON DELETE CASCADE
);
CREATE UNIQUE INDEX vote_unique_idx
  ON VOTES (user_id, date);