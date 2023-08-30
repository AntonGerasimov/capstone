package com.gerasimov.capstone.dbclasses.services;

import com.gerasimov.capstone.dbclasses.domain.DishDto;

import java.util.List;

public interface DishService {
    List<DishDto> getAllDishes();
}
