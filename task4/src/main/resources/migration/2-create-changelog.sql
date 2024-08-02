CREATE TABLE model
(
    id         BIGINT DEFAULT nextval('model_id_sequence') PRIMARY KEY,
    car_type   VARCHAR(32) NOT NULL,
    brand      VARCHAR(64) NOT NULL,
    model_name VARCHAR(64) NOT NULL,
    UNIQUE (car_type, brand, model_name)
);

CREATE TABLE customer
(
    id           BIGINT DEFAULT nextval('customer_id_sequence') PRIMARY KEY,
    full_name    VARCHAR(128)       NOT NULL,
    email        VARCHAR(64) UNIQUE,
    phone_number VARCHAR(32) UNIQUE NOT NULL,
    address      VARCHAR(128)
);

CREATE TABLE employee
(
    id        BIGINT DEFAULT nextval('employee_id_sequence') PRIMARY KEY,
    full_name VARCHAR(128)   NOT NULL,
    position  VARCHAR(64)    NOT NULL,
    hire_date DATE           NOT NULL,
    salary    NUMERIC(16, 3) NOT NULL
);

CREATE TABLE discount
(
    id         BIGINT DEFAULT nextval('discount_id_sequence') PRIMARY KEY,
    percentage NUMERIC(6, 3) NOT NULL,
    start_date DATE          NOT NULL,
    end_date   DATE          NOT NULL,
    model_id   BIGINT        NOT NULL
);

CREATE TABLE car
(
    id              BIGINT DEFAULT nextval('car_id_sequence') PRIMARY KEY,
    model_id        BIGINT REFERENCES model (id) NOT NULL,
    color           VARCHAR(64)                  NOT NULL,
    production_date DATE                         NOT NULL,
    price           NUMERIC(16, 3)               NOT NULL,
    mileage         BIGINT               NOT NULL
);

CREATE TABLE analytics
(
    id                BIGINT DEFAULT nextval('analytics_id_sequence') PRIMARY KEY,
    car_id            BIGINT REFERENCES car (id)      NOT NULL,
    customer_id       BIGINT REFERENCES customer (id) NOT NULL,
    sale_date         DATE                            NOT NULL,
    sale_assistant_id BIGINT REFERENCES employee (id) NOT NULL,
    sold_price        NUMERIC(16, 3)                  NOT NULL,
    discount_id       BIGINT REFERENCES discount (id)
);