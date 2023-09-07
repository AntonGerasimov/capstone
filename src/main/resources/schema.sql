CREATE DATABASE IF NOT EXISTS restaurantdb;

USE restaurantdb;

DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE roles
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(14)
) ENGINE=InnoDB;

CREATE TABLE users
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    first_name      VARCHAR(30),
    last_name       VARCHAR(30),
    email           VARCHAR(50) NOT NULL UNIQUE,
    username        VARCHAR(30),
    password        VARCHAR(64),
    role_id         INT,
    is_active       BOOLEAN,
    CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES roles (id)

) ENGINE=InnoDB;

CREATE TABLE addresses
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    user_id         INT,
    street          VARCHAR(20),
    house           VARCHAR(5),
    apartment       VARCHAR(5),
    CONSTRAINT fk_address_user FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;


CREATE TABLE orders
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    customer_id         INT,
    created             DATETIME,
    status              VARCHAR(14),
    delivery_address_id INT,
    CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES users (id),
    CONSTRAINT fk_order_address FOREIGN KEY (delivery_address_id) REFERENCES addresses (id)
) ENGINE=InnoDB;

CREATE TABLE dishes
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(14),
    description     VARCHAR(200),
    category        VARCHAR(50),
    price           DECIMAL(8, 2),
    is_available    BOOLEAN
) ENGINE=InnoDB;

CREATE TABLE order_items
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    order_id        INT NOT NULL,
    dish_id         INT NOT NULL,
    dish_price      DECIMAL(8, 2),
    quantity        INT NOT NULL CHECK (quantity > 0),
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (dish_id) REFERENCES dishes (id)
) ENGINE=InnoDB;

COMMIT;