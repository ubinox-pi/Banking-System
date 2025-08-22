-- Users table
CREATE TABLE users
(
    userid                BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    first_name            VARCHAR(50)  NOT NULL,
    middle_name           VARCHAR(50)  NOT NULL,
    last_name             VARCHAR(50)  NOT NULL,
    date_of_birth         DATE         NOT NULL,
    gender                VARCHAR(10)  NOT NULL,
    father_name           VARCHAR(100) NOT NULL,
    mother_name           VARCHAR(100) NOT NULL,
    marital_status        VARCHAR(20)  NOT NULL,
    spouse_name           VARCHAR(100),
    occupation            VARCHAR(50)  NOT NULL,
    salary                VARCHAR(20)  NOT NULL,
    citizen               VARCHAR(20)  NOT NULL,
    category              VARCHAR(20)  NOT NULL,
    religion              VARCHAR(20)  NOT NULL,
    account_interest_rate FLOAT        NOT NULL DEFAULT 0.0,
    is_active             BOOLEAN,
    is_blocked            BOOLEAN,
    is_deleted            BOOLEAN,
    status                VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
    description           TEXT,
    created_at            TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at            TIMESTAMP    NOT NULL DEFAULT now()
);

-- Mapping table for multiple account_ids per user
CREATE TABLE user_accounts
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT NOT NULL REFERENCES users (userid) ON DELETE CASCADE,
    account_id BIGINT UNIQUE,
    UNIQUE (user_id, account_id)
);

-- Contact Details table
CREATE TABLE contact_details
(
    contact_id              BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id                 BIGINT UNIQUE       NOT NULL REFERENCES users (userid) ON DELETE CASCADE,
    mobile_number           VARCHAR(15) UNIQUE  NOT NULL,
    email                   VARCHAR(100) UNIQUE NOT NULL,
    communication_address   TEXT                NOT NULL,
    permanent_address       TEXT                NOT NULL,
    city                    VARCHAR(50)         NOT NULL,
    state                   VARCHAR(50)         NOT NULL,
    zip                     VARCHAR(10)         NOT NULL,
    landmark                TEXT                NOT NULL,
    country                 VARCHAR(50)         NOT NULL,
    alternate_mobile_number VARCHAR(15),
    alternate_email         VARCHAR(100),
    created_at              TIMESTAMP           NOT NULL DEFAULT now(),
    updated_at              TIMESTAMP           NOT NULL DEFAULT now()
);

-- Nominee table
CREATE TABLE nominee
(
    nominee_id            BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id               BIGINT UNIQUE       NOT NULL REFERENCES users (userid) ON DELETE CASCADE,
    nominee_name          VARCHAR(100)        NOT NULL,
    nominee_relationship  VARCHAR(20)         NOT NULL,
    nominee_date_of_birth DATE                NOT NULL,
    nominee_mobile_number VARCHAR(15) UNIQUE  NOT NULL,
    nominee_email         VARCHAR(100) UNIQUE NOT NULL,
    nominee_aadhaar       VARCHAR(16) UNIQUE  NOT NULL,
    nominee_pan           VARCHAR(10) UNIQUE  NOT NULL,
    nominee_address       TEXT                NOT NULL,
    created_at            TIMESTAMP           NOT NULL DEFAULT now(),
    updated_at            TIMESTAMP           NOT NULL DEFAULT now()
);

-- KYC table
CREATE TABLE kyc
(
    kyc_id                   BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id                  BIGINT UNIQUE      NOT NULL REFERENCES users (userid) ON DELETE CASCADE,
    aadhaar_number           VARCHAR(12) UNIQUE NOT NULL,
    aadhaar_image            BYTEA              NOT NULL,
    aadhaar_verified         BOOLEAN                     DEFAULT FALSE,
    pan_number               VARCHAR(10) UNIQUE NOT NULL,
    pan_image                BYTEA              NOT NULL,
    pan_verified             BOOLEAN                     DEFAULT FALSE,
    user_photo               BYTEA              NOT NULL,
    user_photo_verified      BOOLEAN                     DEFAULT FALSE,
    user_signature           BYTEA              NOT NULL,
    user_signature_verified  BOOLEAN                     DEFAULT FALSE,
    voter_id                 VARCHAR(20),
    voter_id_image           BYTEA,
    voter_id_verified        BOOLEAN                     DEFAULT FALSE,
    passport_number          VARCHAR(20),
    passport_image           BYTEA,
    passport_verified        BOOLEAN                     DEFAULT FALSE,
    driving_license_number   VARCHAR(20),
    driving_license_image    BYTEA,
    driving_license_verified BOOLEAN                     DEFAULT FALSE,
    verified_by_employee_id  BIGINT,
    status                   VARCHAR(20)        NOT NULL DEFAULT 'PENDING',
    rejection_reason         TEXT,
    created_at               TIMESTAMP          NOT NULL DEFAULT now(),
    updated_at               TIMESTAMP          NOT NULL DEFAULT now()
);
