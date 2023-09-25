package com.gerasimov.capstone.service;

import com.gerasimov.capstone.domain.AddressDtoLight;
import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.entity.Dish;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface DishService {
    List<DishDto> findAll();

    List<DishDto> findAvailable();

    List<DishDto> findHotSale();

    DishDto findById(Long id);

    List<DishDto> getCartItems(HttpSession httpSession);

    DishDto save(DishDto dishDto);

    void update(DishDto dishDto);

    void delete(Long dishId);
}
