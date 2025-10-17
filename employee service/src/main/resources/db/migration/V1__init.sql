-- V1__create_employee_table.sql
CREATE TABLE employee
(
    employee_id                BIGSERIAL PRIMARY KEY,
    first_name                 VARCHAR(255) NOT NULL,
    middle_name                VARCHAR(255) NOT NULL,
    last_name                  VARCHAR(255) NOT NULL,
    date_of_birth              DATE         NOT NULL,
    email                      VARCHAR(255) NOT NULL UNIQUE,
    mobile_number              VARCHAR(20)  NOT NULL UNIQUE,
    username                   VARCHAR(100) NOT NULL UNIQUE,
    password                   VARCHAR(255) NOT NULL,
    roles                      VARCHAR(50)  NOT NULL,
    gender                     VARCHAR(20)  NOT NULL,
    is_account_non_expired     BOOLEAN      NOT NULL DEFAULT TRUE,
    is_account_non_locked      BOOLEAN      NOT NULL DEFAULT TRUE,
    is_credentials_non_expired BOOLEAN      NOT NULL DEFAULT TRUE,
    is_enabled                 BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at                 TIMESTAMP    NOT NULL,
    updated_at                 TIMESTAMP    NOT NULL
);

INSERT INTO employee (first_name,
                      middle_name,
                      last_name,
                      date_of_birth,
                      email,
                      mobile_number,
                      username,
                      password,
                      roles,
                      gender,
                      created_at,
                      updated_at)
VALUES ('ASHISH',
        'KUMAR',
        'KUSHWAHA',
        '2005-10-03',
        'ashish23481@gmail.com',
        '9801112671',
        'admin',
        '$2a$10$27JgxzD7Ddthnjp4KrMCw.jhqRu3GQcOxfBx1epXpACcNMx3nmQaS',
        'ADMIN',
        'MALE',
        '2025-08-18 16:50:13.510091',
        '2025-08-18 16:50:13.510091');

