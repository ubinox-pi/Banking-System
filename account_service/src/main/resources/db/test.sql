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


INSERT INTO branches (branch_name, branch_code, branch_address, created_at, updated_at)
VALUES ('BRANCH 1', 'BRC001', '123 MAIN STREET, CITY A', NOW(), NOW()),
       ('BRANCH 2', 'BRC002', '456 HIGH STREET, CITY B', NOW(), NOW()),
       ('BRANCH 3', 'BRC003', '789 PARK AVENUE, CITY C', NOW(), NOW()),
       ('BRANCH 4', 'BRC004', '101 CENTRAL AVENUE, CITY D', NOW(), NOW()),
       ('BRANCH 5', 'BRC005', '202 WEST END, CITY E', NOW(), NOW()),
       ('BRANCH 6', 'BRC006', '303 EAST SIDE, CITY F', NOW(), NOW()),
       ('BRANCH 7', 'BRC007', '404 SOUTH ROAD, CITY G', NOW(), NOW()),
       ('BRANCH 8', 'BRC008', '505 NORTH SQUARE, CITY H', NOW(), NOW()),
       ('BRANCH 9', 'BRC009', '606 GREENWAY, CITY I', NOW(), NOW()),
       ('BRANCH 10', 'BRC010', '707 BLUE STREET, CITY J', NOW(), NOW()),
       ('BRANCH 11', 'BRC011', '808 GOLD AVENUE, CITY K', NOW(), NOW()),
       ('BRANCH 12', 'BRC012', '909 SILVER ROAD, CITY L', NOW(), NOW()),
       ('BRANCH 13', 'BRC013', '111 CRYSTAL STREET, CITY M', NOW(), NOW()),
       ('BRANCH 14', 'BRC014', '222 EMERALD ROAD, CITY N', NOW(), NOW()),
       ('BRANCH 15', 'BRC015', '333 RUBY AVENUE, CITY O', NOW(), NOW()),
       ('BRANCH 16', 'BRC016', '444 DIAMOND STREET, CITY P', NOW(), NOW()),
       ('BRANCH 17', 'BRC017', '555 TOPAZ ROAD, CITY Q', NOW(), NOW()),
       ('BRANCH 18', 'BRC018', '666 OPAL STREET, CITY R', NOW(), NOW()),
       ('BRANCH 19', 'BRC019', '777 SAPPHIRE ROAD, CITY S', NOW(), NOW()),
       ('BRANCH 20', 'BRC020', '888 GARNET AVENUE, CITY T', NOW(), NOW());


INSERT INTO account (user_id, branch_id, account_type, account_number, balance, status,
                     mode_of_operation, account_interest_rate, created_at, updated_at)
VALUES (1, 1, 'SAVINGS', 'ACCT0000001', 10000, 'ACTIVE', 'SINGLE', '4.00%', NOW(), NOW()),
       (2, 2, 'CURRENT', 'ACCT0000002', 25000, 'ACTIVE', 'JOINT', '4.00%', NOW(), NOW()),
       (3, 3, 'SAVINGS', 'ACCT0000003', 15000, 'ACTIVE', 'SINGLE', '4.00%', NOW(), NOW()),
       (4, 4, 'CURRENT', 'ACCT0000004', 50000, 'ACTIVE', 'JOINT', '4.00%', NOW(), NOW()),
       (5, 5, 'SAVINGS', 'ACCT0000005', 12000, 'ACTIVE', 'SINGLE', '4.00%', NOW(), NOW()),
       (6, 6, 'CURRENT', 'ACCT0000006', 80000, 'ACTIVE', 'JOINT', '4.00%', NOW(), NOW()),
       (7, 7, 'SAVINGS', 'ACCT0000007', 30000, 'ACTIVE', 'SINGLE', '4.00%', NOW(), NOW()),
       (8, 8, 'CURRENT', 'ACCT0000008', 47000, 'ACTIVE', 'JOINT', '4.00%', NOW(), NOW()),
       (9, 9, 'SAVINGS', 'ACCT0000009', 5200, 'ACTIVE', 'SINGLE', '4.00%', NOW(), NOW()),
       (10, 10, 'CURRENT', 'ACCT0000010', 11000, 'ACTIVE', 'JOINT', '4.00%', NOW(), NOW()),
       (11, 11, 'SAVINGS', 'ACCT0000011', 9800, 'ACTIVE', 'SINGLE', '4.00%', NOW(), NOW()),
       (12, 12, 'CURRENT', 'ACCT0000012', 102000, 'ACTIVE', 'JOINT', '4.00%', NOW(), NOW()),
       (13, 13, 'SAVINGS', 'ACCT0000013', 7600, 'ACTIVE', 'SINGLE', '4.00%', NOW(), NOW()),
       (14, 14, 'CURRENT', 'ACCT0000014', 43000, 'ACTIVE', 'JOINT', '4.00%', NOW(), NOW()),
       (15, 15, 'SAVINGS', 'ACCT0000015', 39000, 'ACTIVE', 'SINGLE', '4.00%', NOW(), NOW()),
       (16, 16, 'CURRENT', 'ACCT0000016', 22000, 'ACTIVE', 'JOINT', '4.00%', NOW(), NOW()),
       (17, 17, 'SAVINGS', 'ACCT0000017', 16000, 'ACTIVE', 'SINGLE', '4.00%', NOW(), NOW()),
       (18, 18, 'CURRENT', 'ACCT0000018', 72000, 'ACTIVE', 'JOINT', '4.00%', NOW(), NOW()),
       (19, 19, 'SAVINGS', 'ACCT0000019', 31000, 'ACTIVE', 'SINGLE', '4.00%', NOW(), NOW()),
       (20, 20, 'CURRENT', 'ACCT0000020', 18000, 'ACTIVE', 'JOINT', '4.00%', NOW(), NOW());
