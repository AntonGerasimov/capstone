package com.gerasimov.capstone.cart;

import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.domain.OrderItemDto;
import com.gerasimov.capstone.service.DishService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartService {
    private DishService dishService;
    @Bean
    public Cart cart() {
        return new Cart();
    }

    public void addToCart(Long dishId, Cart cart){
        DishDto dishDto = dishService.findById(dishId);
        cart.addDish(dishDto);
    }

}
