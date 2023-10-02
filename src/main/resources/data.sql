-- DML

INSERT INTO roles (name) VALUES
    ('ROLE_common'),
    ('ROLE_manager'),
    ('ROLE_admin');

INSERT INTO users (first_name, last_name, email, username, password, role_id, is_active) VALUES
    ('admin', 'admin', 'admin@gmail.com', 'admin',
    '$2a$12$WocppmSlhS5v.r8CN4SSUeJuCdwh6ZhQMOANEn.jh/knDCn4xy.Bi', 3, TRUE),
    ('John', 'Doe', 'john@gmail.com',  'john',
    '$2a$12$WocppmSlhS5v.r8CN4SSUeJuCdwh6ZhQMOANEn.jh/knDCn4xy.Bi', 2, TRUE),
    ('Jimmy', 'Page', 'jimmy@gmail.com', 'jimmy',
    '$2a$12$WocppmSlhS5v.r8CN4SSUeJuCdwh6ZhQMOANEn.jh/knDCn4xy.Bi', 3, TRUE),
    ('Vasya', 'Pupkin', 'pupkin@gmail.com', 'vasya',
    '$2a$12$WocppmSlhS5v.r8CN4SSUeJuCdwh6ZhQMOANEn.jh/knDCn4xy.Bi', 1, TRUE),
    ('John', 'Bonham', 'bonham@gmail.com', 'bonham',
    '$2a$12$WocppmSlhS5v.r8CN4SSUeJuCdwh6ZhQMOANEn.jh/knDCn4xy.Bi', 1, TRUE),
    ('Robert', 'Plant', 'plant@gmail.com', 'plant',
    '$2a$12$WocppmSlhS5v.r8CN4SSUeJuCdwh6ZhQMOANEn.jh/knDCn4xy.Bi', 1, FALSE);

INSERT INTO addresses (user_id, street, house, apartment, is_active) VALUES
    (2, 'elm', '12', '23', TRUE),
    (2, 'pine', '21', '1A', TRUE),
    (2, 'oak', '415', '58', TRUE),
    (1, 'pine', '43', '1', TRUE),
    (3, 'oak', '1', '2', TRUE),
    (4, 'palm', '5', '415', TRUE),
    (5, 'sandalwood', '51B', '91', TRUE),
    (6, 'mango', '26', '2B', TRUE);

INSERT INTO orders (customer_id, created, status, delivery_address_id, is_active) VALUES
    (2, '2023-08-30 14:30:00', 'Preparing', 1, TRUE),
    (2, '2023-08-30 14:03:09', 'Cooking', 1, TRUE),
    (2, '2023-08-30 13:30:48', 'Out for Delivery', 1, TRUE),
    (2, '2023-08-31 18:30:00', 'Delivered', 1, TRUE),
    (2, '2023-08-31 17:30:00', 'Delivered', 1, TRUE),
    (2, '2023-08-31 16:30:00', 'Delivered', 1, TRUE),
    (3, '2023-08-07 16:30:00', 'Delivered', 3, TRUE),
    (4, '2023-08-08 16:30:00', 'Delivered', 4, TRUE),
    (5, '2023-08-09 16:30:00', 'Delivered', 5, TRUE);

INSERT INTO dishes (name, description, category, price, is_available) VALUES
    ('Shwarma',
        'A tantalizing Middle Eastern delight, our shawarma is a savory masterpiece of marinated and spit-roasted meat, thinly sliced to perfection. ',
        'Shwarma','5.5', FALSE),
    ('Margherita Pizza',
        'A classic Italian favorite with a thin, crispy crust topped with rich tomato sauce, fresh mozzarella cheese, fragrant basil leaves, and a drizzle of olive oil.',
        'Pizza','12.99', TRUE),
    ('Pepperoni Lovers Pizza',
        'For pepperoni enthusiasts! Our hand-tossed crust is loaded with zesty tomato sauce, generous layers of pepperoni, and a blend of melted mozzarella and cheddar cheese.',
        'Pizza','14.99', TRUE),
    ('BBQ Chicken Pizza',
        'A mouthwatering fusion of flavors! Tender pieces of grilled chicken are smothered in tangy barbecue sauce, combined with red onions, cilantro, and mozzarella cheese atop our signature crust.',
        'Pizza','15.99', TRUE),
    ('Veggie Delight Pizza',
        'A garden-fresh delight! This pizza is adorned with a medley of colorful vegetables, including bell peppers, onions, mushrooms, black olives, and spinach, all on a whole wheat crust.',
        'Pizza','13.99', TRUE),
    ('Supreme Meat Feast Pizza',
        'Meat lovers dream! This pizza is loaded with a hearty combination of pepperoni, sausage, bacon, and ground beef, along with bell peppers, onions, and mozzarella cheese.',
        'Pizza','16.99', TRUE),
    ('Classic Cola',
        'The timeless favorite, a fizzy and refreshing cola.',
        'Bevarages','2.49', TRUE),
    ('Iced Tea',
        'Cool and revitalizing iced tea, served with a slice of lemon.',
        'Bevarages','2.99', TRUE),
    ('Sparkling Water',
        'Effervescent and crisp, a pure and simple way to quench your thirst.',
        'Bevarages','1.99', TRUE),
    ('Mango Tango Smoothie',
        'Indulge in the sweet and exotic taste of ripe mangoes, blended to perfection.',
        'Bevarages','3.99', TRUE);


INSERT INTO order_items (order_id, dish_id, dish_price, quantity) VALUES
    (1, 1, 12.99, 2),
    (2, 2, 14.99, 2),
    (3, 3, 15.99, 2),
    (4, 1, 12.99, 1),
    (4, 2, 14.99, 2),
    (4, 3, 15.99, 3),
    (4, 4, 13.99, 4),
    (4, 5, 16.99, 5),
    (5, 1, 12.99, 1),
    (5, 2, 14.99, 2),
    (5, 3, 15.99, 3),
    (5, 4, 13.99, 4),
    (5, 5, 16.99, 5),
    (6, 1, 12.99, 1),
    (6, 2, 14.99, 2),
    (6, 3, 15.99, 3),
    (6, 4, 13.99, 4),
    (6, 5, 16.99, 5);

COMMIT;