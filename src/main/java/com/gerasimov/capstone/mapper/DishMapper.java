package com.gerasimov.capstone.mapper;

import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.entity.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DishMapper {
    DishMapper INSTANCE = Mappers.getMapper(DishMapper.class);

    DishDto toDto(Dish dish);

    Dish toEntity(DishDto dishDto);
}
