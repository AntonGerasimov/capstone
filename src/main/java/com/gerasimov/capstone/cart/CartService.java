package com.gerasimov.capstone.cart;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Bean
    public Cart cart() {
        return new Cart();
    }
}
