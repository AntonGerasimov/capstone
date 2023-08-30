package com.gerasimov.capstone.dbclasses.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DISHES")
@Getter
@Setter
public class DishEntity {
    @Id
    private Long dishId;
    private String dishName;
    private String description;
    private Double price;
    private boolean isAvailable;
}

//    CREATE TABLE IF NOT EXISTS DISHES
//        (
//                DISH_ID         INT NOT NULL,
//                DISH_NAME       VARCHAR(14),
//    DESCRIPTION     VARCHAR(200),
//    PRICE           DECIMAL,
//    IS_AVAILABLE    BOOLEAN,
//    PRIMARY KEY (DISH_ID)
//) ENGINE=InnoDB;