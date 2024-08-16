CREATE TABLE users
(
    id       BIGINT DEFAULT nextval('user_id_sequence') PRIMARY KEY,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL
);

CREATE TABLE role
(
    id   BIGINT DEFAULT nextval('role_id_sequence') PRIMARY KEY,
    authority VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE users_roles (
    user_id BIGINT REFERENCES users(id) NOT NULL,
    role_id BIGINT REFERENCES role(id) NOT NULL,
    UNIQUE (user_id, role_id)
);

CREATE TABLE token_pair(
    id BIGINT DEFAULT nextval('token_pair_id_sequence') PRIMARY KEY ,
    access_token VARCHAR NOT NULL ,
    refresh_token VARCHAR NOT NULL ,
    user_id BIGINT REFERENCES users(id)
);