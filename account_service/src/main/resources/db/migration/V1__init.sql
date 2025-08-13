CREATE TABLE branches
(
    branch_id      BIGSERIAL PRIMARY KEY,
    branch_name    VARCHAR(255) NOT NULL,
    branch_code    VARCHAR(255) NOT NULL UNIQUE,
    branch_address TEXT         NOT NULL,
    created_at     TIMESTAMP    NOT NULL,
    updated_at     TIMESTAMP    NOT NULL
);

CREATE TABLE account
(
    account_id            BIGSERIAL PRIMARY KEY,
    user_id               BIGINT       NOT NULL,
    branch_id             BIGINT       NOT NULL,
    account_type          VARCHAR(255) NOT NULL,
    account_number        VARCHAR(255) NOT NULL UNIQUE,
    balance               NUMERIC(38, 0),
    status                VARCHAR(255) NOT NULL,
    mode_of_operation     VARCHAR(255) NOT NULL,
    account_interest_rate VARCHAR(255) NOT NULL,
    created_at            TIMESTAMP    NOT NULL,
    updated_at            TIMESTAMP    NOT NULL
);
