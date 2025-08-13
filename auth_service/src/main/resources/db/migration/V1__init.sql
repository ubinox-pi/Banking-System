CREATE TYPE roles AS ENUM ('ADMIN', 'USER', 'EMPLOYEE');

CREATE TABLE users
(
    id                     BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    username               VARCHAR(100) UNIQUE NOT NULL,
    password               VARCHAR(255)        NOT NULL,
    roles                  VARCHAR(20)         NOT NULL,
    is_active              BOOLEAN DEFAULT TRUE,
    is_expired             BOOLEAN DEFAULT FALSE,
    is_locked              BOOLEAN DEFAULT FALSE,
    is_credentials_expired BOOLEAN DEFAULT FALSE
);

