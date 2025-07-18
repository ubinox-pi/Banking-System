-- User table
CREATE TABLE users
(
    userid                SERIAL PRIMARY KEY,
    first_name            VARCHAR(50)   NOT NULL,
    middle_name           VARCHAR(50)   NOT NULL,
    last_name             VARCHAR(50)   NOT NULL,
    date_of_birth         DATE          NOT NULL,
    gender                VARCHAR(10)   NOT NULL,
    father_name           VARCHAR(100)  NOT NULL,
    mother_name           VARCHAR(100)  NOT NULL,
    marital_status        VARCHAR(20)   NOT NULL,
    spouse_name           VARCHAR(100),
    occupation            VARCHAR(50)   NOT NULL,
    salary                VARCHAR(20)   NOT NULL,
    citizen               VARCHAR(20)   NOT NULL,
    category              VARCHAR(20)   NOT NULL,
    religion              VARCHAR(20)   NOT NULL,
    account_id            BIGINT UNIQUE,
    account_interest_rate NUMERIC(5, 2) NOT NULL DEFAULT 0.0,
    is_active             BOOLEAN,
    is_blocked            BOOLEAN,
    is_deleted            BOOLEAN,
    status                VARCHAR(20)   NOT NULL DEFAULT 'PENDING',
    description           TEXT,
    created_at            TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at            TIMESTAMP     NOT NULL DEFAULT now()
);

-- Contact Details table
CREATE TABLE contact_details
(
    contact_id              SERIAL PRIMARY KEY,
    user_id                 INTEGER UNIQUE      NOT NULL REFERENCES users (userid) ON DELETE CASCADE,
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
    nominee_id            SERIAL PRIMARY KEY,
    user_id               INTEGER UNIQUE      NOT NULL REFERENCES users (userid) ON DELETE CASCADE,
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
    kyc_id                   SERIAL PRIMARY KEY,
    user_id                  INTEGER UNIQUE     NOT NULL REFERENCES users (userid) ON DELETE CASCADE,
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
    voter_id                 VARCHAR(20) UNIQUE,
    voter_id_image           BYTEA,
    voter_id_verified        BOOLEAN                     DEFAULT FALSE,
    passport_number          VARCHAR(20) UNIQUE,
    passport_image           BYTEA,
    passport_verified        BOOLEAN                     DEFAULT FALSE,
    driving_license_number   VARCHAR(20) UNIQUE,
    driving_license_image    BYTEA,
    driving_license_verified BOOLEAN                     DEFAULT FALSE,
    verified_by_employee_id  BIGINT,
    rejection_reason         TEXT,
    created_at               TIMESTAMP          NOT NULL DEFAULT now(),
    updated_at               TIMESTAMP          NOT NULL DEFAULT now()
);

-- Sample data for users
INSERT INTO users (first_name, middle_name, last_name, date_of_birth, gender, father_name, mother_name, marital_status,
                   spouse_name, occupation, salary, citizen, category, religion, account_id)
VALUES ('Amit', 'Kumar', 'Singh', '1990-05-15', 'MALE', 'Rajesh Singh', 'Sunita Singh', 'MARRIED', 'Priya Singh',
        'ENGINEER', '95000', 'Indian', 'GEN', 'HINDU', 10000001),
       ('Priya', 'Rani', 'Verma', '1985-08-22', 'FEMALE', 'Suresh Verma', 'Meena Verma', 'MARRIED', 'Amit Verma',
        'TEACHER', '65000', 'Indian', 'OBC', 'HINDU', 10000002),
       ('Rahul', 'Narayan', 'Yadav', '1992-11-10', 'MALE', 'Mahesh Yadav', 'Kavita Yadav', 'UNMARRIED', NULL, 'DOCTOR',
        '120000', 'Indian', 'SC', 'HINDU', 10000003);

-- Sample data for contact_details
INSERT INTO contact_details (user_id, mobile_number, email, communication_address, permanent_address, city, state, zip,
                             landmark, country, alternate_mobile_number, alternate_email)
VALUES (1, '+919812345678', 'amit.singh@example.com', '123 MG Road, Delhi', '456 Park Street, Delhi', 'Delhi', 'Delhi',
        '110001', 'Near Metro Station', 'India', '+919876543210', 'amit.alt@example.com'),
       (2, '+919876543210', 'priya.verma@example.com', '789 Lake View, Mumbai', '321 Hill Road, Mumbai', 'Mumbai',
        'Maharashtra', '400001', 'Opposite City Mall', 'India', '+919812345678', 'priya.alt@example.com'),
       (3, '+919900112233', 'rahul.yadav@example.com', '12 Sector 5, Lucknow', '34 Sector 7, Lucknow', 'Lucknow',
        'Uttar Pradesh', '226001', 'Near Gomti Nagar', 'India', '+919900223344', 'rahul.alt@example.com');

-- Sample data for nominee
INSERT INTO nominee (user_id, nominee_name, nominee_relationship, nominee_date_of_birth, nominee_mobile_number,
                     nominee_email, nominee_aadhaar, nominee_pan, nominee_address)
VALUES (1, 'Rohit Singh', 'SON', '2012-03-10', '+919900112233', 'rohit.singh@example.com', '123412341234', 'ABCDE1234F',
        '123 MG Road, Delhi'),
       (2, 'Anjali Verma', 'DAUGHTER', '2010-07-25', '+919900223344', 'anjali.verma@example.com', '432143214321',
        'XYZAB9876K', '789 Lake View, Mumbai'),
       (3, 'Kavya Yadav', 'SISTER', '1995-01-15', '+919900334455', 'kavya.yadav@example.com', '567856785678',
        'LMNOP1234Q', '12 Sector 5, Lucknow');

-- Sample data for kyc
INSERT INTO kyc (user_id, aadhaar_number, aadhaar_image, pan_number, pan_image, user_photo, user_signature)
VALUES (1, '123456789012', decode('', 'hex'), 'ABCDE1234F', decode('', 'hex'), decode('', 'hex'), decode('', 'hex')),
       (2, '987654321098', decode('', 'hex'), 'XYZAB9876K', decode('', 'hex'), decode('', 'hex'), decode('', 'hex')),
       (3, '567856785678', decode('', 'hex'), 'LMNOP1234Q', decode('', 'hex'), decode('', 'hex'), decode('', 'hex'));
