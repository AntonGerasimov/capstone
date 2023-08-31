package com.gerasimov.capstone.dbclasses.services;

import com.gerasimov.capstone.dbclasses.domain.DishDto;
import com.gerasimov.capstone.dbclasses.entity.Dish;
import com.gerasimov.capstone.dbclasses.mappers.DishMapper;
import com.gerasimov.capstone.dbclasses.repositories.DishRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    @Override
    public List<DishDto> getAllDishes() {
        List<Dish> dishEntities = dishRepository.findAll();
        List<DishDto> dishes = dishEntities.stream()
                .map(dishMapper::mapDishEntityToDomain)
                .collect(Collectors.toList());
        return dishes;
    }
}
