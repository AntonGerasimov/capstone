package com.gerasimov.capstone.dbclasses.mappers;

import com.gerasimov.capstone.dbclasses.domain.DishDto;
import com.gerasimov.capstone.dbclasses.entity.Dish;
import com.gerasimov.capstone.dbclasses.repositories.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DishMapper {

    private final DishRepository dishRepository;

    @Autowired
    public DishMapper(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public DishDto mapDishEntityToDomain(Dish dishEntity){
        Long id = dishEntity.getDishId();
        String name = dishEntity.getDishName();
        String description = dishEntity.getDescription();
        double price = dishEntity.getPrice();
        boolean isAvailable = dishEntity.isAvailable();
        return new DishDto(id, name, description, price, isAvailable);
    }
}

//    private final Long dishId;
//    private final String dishName;
//    private final String description;
//    private final double price;
//    private final boolean isAvailable;