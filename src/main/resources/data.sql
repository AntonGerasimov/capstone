-- DML

INSERT IGNORE INTO roles (role_name) VALUES
    ('admin'),
    ('manager'),
    ('common');

INSERT IGNORE INTO users (first_name, last_name, email, username, password, role_id, is_active) VALUES
    ('John', 'Doe', '2@gmail.com',  'john', 'john', 2, TRUE),
    ('Jimmy', 'Page', '1@gmail.com', 'admin', 'admin', 1, TRUE);

INSERT INTO addresses (user_id, street, house, apartment) VALUES
    (2, 'elm', '12', '23'),
    (1, 'pine', '43', '1');

INSERT INTO orders (customer_id, created, status, delivery_address_id) VALUES
    (2, '2023-08-28 14:30:00', 'active', 2),
    (1, '2023-08-28 14:30:00', 'active', 1);

COMMIT;