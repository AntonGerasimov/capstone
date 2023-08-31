package com.gerasimov.capstone.dbclasses.mappers;

import com.gerasimov.capstone.dbclasses.domain.DishDto;
import com.gerasimov.capstone.dbclasses.entity.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DishMapper {
    DishMapper INSTANCE = Mappers.getMapper(DishMapper.class);
    DishDto mapDishEntityToDomain(Dish dishEntity);
}
