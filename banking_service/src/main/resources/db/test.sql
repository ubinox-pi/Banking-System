-- Table: banking
CREATE TABLE banking
(
    bank_id                       BIGSERIAL PRIMARY KEY,
    user_id                       BIGINT       NOT NULL UNIQUE,
    username                      VARCHAR(20)  NOT NULL UNIQUE,
    password                      VARCHAR(255) NOT NULL,
    recovery_phrase               VARCHAR(255) NOT NULL DEFAULT 'WHAT_IS_YOUR_BIRTH_CITY',
    recovery_answer               VARCHAR(255) NOT NULL DEFAULT 'JAMSHEDPUR',
    is_account_is_not_expired     BOOLEAN      NOT NULL DEFAULT FALSE,
    is_account_is_not_locked      BOOLEAN      NOT NULL DEFAULT FALSE,
    is_credentials_is_not_expired BOOLEAN      NOT NULL DEFAULT FALSE,
    is_active                     BOOLEAN      NOT NULL DEFAULT FALSE,
    is_first_logged_in_success    BOOLEAN      NOT NULL DEFAULT FALSE,
    history                       TEXT
);

-- Table: login_history
CREATE TABLE login_history
(
    login_history_id BIGSERIAL PRIMARY KEY,
    bank_id          BIGINT       NOT NULL,
    logout_time      TIMESTAMP,
    login_time       TIMESTAMP    NOT NULL,
    ip_address       VARCHAR(255) NOT NULL,
    device_info      VARCHAR(100),
    location         VARCHAR(100),
    failure_reason   VARCHAR(200) NOT NULL,
    is_suspicious    BOOLEAN      NOT NULL,
    mfa_method_used  VARCHAR(255),
    status           VARCHAR(50),

    CONSTRAINT fk_login_history_banking FOREIGN KEY (bank_id)
        REFERENCES banking (bank_id)
        ON DELETE CASCADE
);

-- Optional index to speed up login history queries
CREATE INDEX idx_login_history_bank_id ON login_history (bank_id);
CREATE INDEX idx_login_history_login_time ON login_history (login_time DESC);
