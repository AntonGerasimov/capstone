-- DML

INSERT IGNORE INTO roles (role_name) VALUES
    ('admin'),
    ('manager'),
    ('common');

INSERT IGNORE INTO users (first_name, last_name, email, username, password, role_id, is_active) VALUES
    ('admin', 'admin', 'admin@gmail.com', 'admin', 'admin', 1, TRUE),
    ('John', 'Doe', 'john@gmail.com',  'john', 'john', 2, TRUE),
    ('Jimmy', 'Page', 'jimmy@gmail.com', 'jimmy', 'jimmy', 1, TRUE),
    ('Vasya', 'Pupkin', 'pupkin@gmail.com', 'vasya', 'vasya', 3, TRUE),
    ('John', 'Bonham', 'bonham@gmail.com', 'bonham', 'bonham', 3, TRUE),
    ('Robert', 'Plant', 'plant@gmail.com', 'plant', 'plant', 3, TRUE);

INSERT INTO addresses (user_id, street, house, apartment) VALUES
    (2, 'elm', '12', '23'),
    (1, 'pine', '43', '1'),
    (3, 'oak', '1', '2'),
    (4, 'palm', '5', '415'),
    (5, 'sandalwood', '51B', '91'),
    (6, 'mango', '26', '2B');

INSERT INTO orders (customer_id, created, status, delivery_address_id) VALUES
    (2, '2023-08-28 14:30:00', 'active', 1),
    (2, '2023-08-29 14:30:00', 'active', 1),
    (2, '2023-08-30 14:30:00', 'active', 1),
    (2, '2023-08-31 14:30:00', 'active', 1),
    (3, '2023-08-07 16:30:00', 'active', 3),
    (4, '2023-08-08 16:30:00', 'active', 4),
    (5, '2023-08-09 16:30:00', 'active', 5);

COMMIT;