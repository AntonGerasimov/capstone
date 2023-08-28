-- DML

INSERT INTO ROLES VALUES
    (1, 'admin'),
    (2, 'manager'),
    (3, 'common');

INSERT INTO USERS VALUES
    (1, 'Jimmy', 'Page', 'admin', 'admin', 1, TRUE),
    (2, 'John', 'Doe', 'john', 'john', 2, TRUE);

INSERT INTO ADDRESSES VALUES
    (1, 2, 'elm', '12', '23'),
    (2, 1, 'pine', '43', '1');

INSERT INTO ORDERS VALUES
    (1, 2, TO_DATE('17-11-2020', 'DD-MM-YYYY'), 'active', 2),
    (2, 1, TO_DATE('7-9-2022', 'DD-MM-YYYY'), 'active', 1);



COMMIT;
