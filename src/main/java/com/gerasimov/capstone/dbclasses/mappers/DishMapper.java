package com.gerasimov.capstone.dbclasses.mappers;

import com.gerasimov.capstone.dbclasses.domain.Dish;
import com.gerasimov.capstone.dbclasses.entity.DishEntity;
import com.gerasimov.capstone.dbclasses.repositories.DishRepository;
import com.gerasimov.capstone.dbclasses.repositories.RoleRepository;
import com.gerasimov.capstone.dbclasses.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DishMapper {

    private final DishRepository dishRepository;

    @Autowired
    public DishMapper(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public Dish mapDishEntityToDomain(DishEntity dishEntity){
        Long id = dishEntity.getDishId();
        String name = dishEntity.getDishName();
        String description = dishEntity.getDescription();
        double price = dishEntity.getPrice();
        boolean isAvailable = dishEntity.isAvailable();
        return new Dish(id, name, description, price, isAvailable);
    }
}

//    private final Long dishId;
//    private final String dishName;
//    private final String description;
//    private final double price;
//    private final boolean isAvailable;