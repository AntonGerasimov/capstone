-- DML

INSERT INTO ROLES VALUES
    (1, 'admin'),
    (2, 'manager'),
    (3, 'common')
    ON DUPLICATE KEY UPDATE ROLE_ID = ROLE_ID;

INSERT IGNORE INTO USERS (FIRST_NAME, LAST_NAME, EMAIL, USERNAME, PASSWORD, ROLE_ID, IS_ACTIVE) VALUES
    ('John', 'Doe', '2@gmail.com',  'john', 'john', 2, TRUE),
    ('Jimmy', 'Page', '1@gmail.com', 'admin', 'admin', 1, TRUE)

    ON DUPLICATE KEY UPDATE USER_ID = USER_ID;

INSERT INTO ADDRESSES VALUES
    (1, 2, 'elm', '12', '23'),
    (2, 1, 'pine', '43', '1')
    ON DUPLICATE KEY UPDATE ADDRESS_ID = ADDRESS_ID;

INSERT INTO ORDERS VALUES
    (1, 2, '2023-08-28 14:30:00', 'active', 2),
    (2, 1, '2023-08-28 14:30:00', 'active', 1)
    ON DUPLICATE KEY UPDATE ORDER_ID = ORDER_ID;


COMMIT;