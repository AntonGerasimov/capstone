-- DML

INSERT IGNORE INTO roles (name) VALUES
    ('common'),
    ('manager'),
    ('admin');

INSERT IGNORE INTO users (first_name, last_name, email, username, password, role_id, is_active) VALUES
    ('admin', 'admin', 'admin@gmail.com', 'admin',
    '$2a$12$elyM3bgM0Sdw877MIFDqxe7EuNUwvpXH14NDHwddStk6pdImuEGpu', 3, TRUE),
    ('John', 'Doe', 'john@gmail.com',  'john',
    '$2a$12$jHbTTshgK7B4Zfg6aLv5x.ZgI709grtzor3hugeXt6WaZ6t3DK3Ie', 2, TRUE),
    ('Jimmy', 'Page', 'jimmy@gmail.com', 'jimmy',
    '$2a$12$B8OMbDf0ju/P2zLJH9UjWeHvo7ldX9Zv8pqJ.UsmVKw1c/bM964Gq', 3, TRUE),
    ('Vasya', 'Pupkin', 'pupkin@gmail.com', 'vasya',
    '$2a$12$Xq52Vb/0UXvC31AMqS2VGuksM.e2o8.BAt3EBOvWtn10qX9jVDZPK', 1, TRUE),
    ('John', 'Bonham', 'bonham@gmail.com', 'bonham',
    '$2a$12$vb1YKkSe5UiS2urR4xPHPuOil6J9nC7AdQxyPRo2AnHakPGOj2J/e', 1, TRUE),
    ('Robert', 'Plant', 'plant@gmail.com', 'plant',
    '$2a$12$emkMqAfaVlv1dJksZmeuEOCCTTVBh8o3LQNAcefBdTfUr1lv7VtI2', 1, TRUE);

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