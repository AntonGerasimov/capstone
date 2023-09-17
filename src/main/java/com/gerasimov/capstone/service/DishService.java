package com.gerasimov.capstone.service;

import com.gerasimov.capstone.domain.DishDto;

import java.util.List;

public interface DishService {
    List<DishDto> findAll();
    List<DishDto> findAvailable();
}
