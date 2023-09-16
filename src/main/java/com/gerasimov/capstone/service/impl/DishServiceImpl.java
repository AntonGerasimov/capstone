package com.gerasimov.capstone.service.impl;

import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.entity.Dish;
import com.gerasimov.capstone.mapper.DishMapper;
import com.gerasimov.capstone.repository.DishRepository;
import com.gerasimov.capstone.service.DishService;
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
        List<Dish> dishEntities = dishRepository.findByIsAvailable();
        return dishEntities.stream()
                .map(dishMapper::toDto)
                .toList();
    }
}
