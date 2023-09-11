package com.gerasimov.capstone.dbclasses.services.impl;

import com.gerasimov.capstone.dbclasses.domain.DishDto;
import com.gerasimov.capstone.dbclasses.entity.Dish;
import com.gerasimov.capstone.dbclasses.mappers.DishMapper;
import com.gerasimov.capstone.dbclasses.repositories.DishRepository;
import com.gerasimov.capstone.dbclasses.services.DishService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    @Override
    public List<DishDto> findAll() {
        List<Dish> dishEntities = dishRepository.findAll();
        return dishEntities.stream()
                .map(dishMapper::toDto)
                .toList();
    }
    @Override
    public List<DishDto> findAllAvailable(){
        List<Dish> dishEntities = dishRepository.findByIsAvailableTrue();
        return dishEntities.stream()
                .map(dishMapper::toDto)
                .toList();
    }
}
