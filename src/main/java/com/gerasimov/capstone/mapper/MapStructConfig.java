package com.gerasimov.capstone.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MapStructConfig {
    @Bean
    public AddressMapper addressMapper() {
        return AddressMapper.INSTANCE;
    }

    @Bean
    public DishMapper dishMapper() {
        return DishMapper.INSTANCE;
    }

    @Bean
    public OrderMapper orderMapper() {
        return OrderMapper.INSTANCE;
    }

    @Bean
    public UserMapper userMapper() {
        return UserMapper.INSTANCE;
    }
}