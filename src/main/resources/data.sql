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
    (1, 'pine', '43', '1', TRUE),
    (3, 'oak', '1', '2', TRUE),
    (4, 'palm', '5', '415', TRUE),
    (5, 'sandalwood', '51B', '91', TRUE),
    (6, 'mango', '26', '2B', TRUE);

INSERT INTO orders (customer_id, created, status, delivery_address_id, is_active) VALUES
    (2, '2023-08-28 14:30:00', 'active', 1, TRUE),
    (2, '2023-08-29 14:30:00', 'active', 1, TRUE),
    (2, '2023-08-30 14:30:00', 'active', 1, TRUE),
    (2, '2023-08-31 14:30:00', 'active', 1, TRUE),
    (3, '2023-08-07 16:30:00', 'active', 3, TRUE),
    (4, '2023-08-08 16:30:00', 'active', 4, TRUE),
    (5, '2023-08-09 16:30:00', 'active', 5, TRUE);

INSERT INTO dishes (name, description, category, price, is_available) VALUES
    ('Pizza Pepperoni',
    'Made of pizza crust, pizza sauce, cheese, and pepperoni. Pepperoni is basically an American version of salami. Pepperoni is a meat mixture of beef and pork that has been cured and seasoned with paprika and chili powder.Pepperoni is characteristically soft, slightly smoky, and bright red in color.',
    'Pizza','10.5', TRUE),
    ('Adjarian khachapuri',
    'Adjarian khachapuri consists of Imeretian cheese, yeast dough, egg and butter. The dough is rolled out into a rectangle, after which its two ends are formed into symmetrical rolls around uniformly distributed portions of cheese sprinkled along the edges.',
    'Georgian Cuisine', '5.1', TRUE),
    ('Dolma',
    'The delicacy from minced meat and rice wrapped in vine leaves',
    'Main dishes', '6.25', TRUE),
    ('Crispy Calamari Rings',
        'Tender calamari rings lightly breaded and fried to perfection. Served with a zesty marinara sauce',
        'Appetizers', '6.25', TRUE),
    ('Spinach and Artichoke Dip',
            'Creamy spinach and artichoke dip, baked with melted cheese, and served with warm tortilla chips',
            'Appetizers', '6.25', TRUE),
    ('Bruschetta Platter',
            'Toasted baguette slices topped with diced tomatoes, fresh basil, garlic, and drizzled with balsamic glaze',
            'Appetizers', '6.25', TRUE),
    ('Mozzarella Sticks',
            'Golden-fried mozzarella sticks served with a side of marinara sauce for dipping',
            'Appetizers', '6.25', TRUE),
    ('Classic Caesar Salad',
            'Crisp romaine lettuce, garlic croutons, and Parmesan cheese tossed in Caesar dressing',
            'Salads', '6.25', TRUE),
    ('Mediterranean Quinoa Salad',
            'Quinoa mixed with cucumbers, tomatoes, red onions, Kalamata olives, feta cheese, and a lemon herb vinaigrette',
            'Salads', '6.25', TRUE),
    ('Grilled Chicken Cobb Salad',
            'Grilled chicken breast, avocado, hard-boiled eggs, bacon, and blue cheese crumbles atop mixed greens with ranch dressing',
            'Salads', '6.25', TRUE),
    ('Dolma',
            'The delicacy from minced meat and rice wrapped in vine leaves',
            'Main dishes', '6.25', TRUE),
    ('Dolma',
            'The delicacy from minced meat and rice wrapped in vine leaves',
            'Main dishes', '6.25', TRUE),
    ('Dolma',
            'The delicacy from minced meat and rice wrapped in vine leaves',
            'Main dishes', '6.25', TRUE);

COMMIT;