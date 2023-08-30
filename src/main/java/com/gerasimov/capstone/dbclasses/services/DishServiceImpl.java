package com.gerasimov.capstone.dbclasses.services;

import com.gerasimov.capstone.dbclasses.domain.Dish;
import com.gerasimov.capstone.dbclasses.domain.User;
import com.gerasimov.capstone.dbclasses.entity.DishEntity;
import com.gerasimov.capstone.dbclasses.mappers.DishMapper;
import com.gerasimov.capstone.dbclasses.mappers.UserMapper;
import com.gerasimov.capstone.dbclasses.repositories.DishRepository;
import com.gerasimov.capstone.dbclasses.repositories.RoleRepository;
import com.gerasimov.capstone.dbclasses.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    @Autowired
    public DishServiceImpl(
            DishRepository dishRepository,
            DishMapper dishMapper) {
        this.dishRepository = dishRepository;
        this.dishMapper = dishMapper;
    }

    @Override
    public List<Dish> getAllDishes() {
        List<DishEntity> dishEntities = dishRepository.findAll();
        List<Dish> dishes = dishEntities.stream()
                .map(dishMapper::mapDishEntityToDomain)
                .collect(Collectors.toList());
        return dishes;
    }
}
