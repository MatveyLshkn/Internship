CREATE TABLE users
(
    id      BIGINT PRIMARY KEY,
    chat_id BIGINT UNIQUE NOT NULL
);

CREATE TABLE brand
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE,
    slug VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE model
(
    id       BIGINT PRIMARY KEY,
    name     VARCHAR(64) NOT NULL,
    slug     VARCHAR(64) NOT NULL,
    brand_id BIGINT      NOT NULL REFERENCES brand (id),
    UNIQUE (name, brand_id)
);

CREATE TABLE preference
(
    model_id BIGINT REFERENCES model (id),
    user_id  BIGINT REFERENCES users (id),
    UNIQUE (model_id, user_id)
);

CREATE TABLE post
(
    id  BIGINT PRIMARY KEY,
    model_id BIGINT  NOT NULL REFERENCES model (id),
    info     VARCHAR,
    url      VARCHAR NOT NULL UNIQUE
);

CREATE TABLE model_check
(
    model_id BIGINT PRIMARY KEY REFERENCES model(id),
    check_date TIMESTAMP NOT NULL
);
