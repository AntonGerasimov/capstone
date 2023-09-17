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
    public AddressLightMapper addressLightMapper() {
        return AddressLightMapper.INSTANCE;
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
    public OrderItemMapper orderItemMapper() {
        return OrderItemMapper.INSTANCE;
    }

    @Bean
    public UserMapper userMapper() {
        return UserMapper.INSTANCE;
    }

}