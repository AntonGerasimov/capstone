package com.gerasimov.capstone.service;

import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.entity.Dish;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface DishService {
    List<DishDto> findAll();
    List<DishDto> findAvailable();
    DishDto findById(Long id);
    List<DishDto> getCartItems(HttpSession httpSession);
    void addToCart();
}
