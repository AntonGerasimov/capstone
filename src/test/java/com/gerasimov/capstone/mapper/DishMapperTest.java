package com.gerasimov.capstone.mapper;

import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.entity.Dish;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class DishMapperTest {

    private final DishMapper dishMapper = DishMapper.INSTANCE;

    @Test
    void testToDtoWhenEntityProvidedThenDtoReturned() {

        Dish dish = new Dish();
        dish.setId(1L);
        dish.setName("Test Dish");
        dish.setDescription("Test Description");
        dish.setCategory("Test Category");
        dish.setPrice(10.0);
        dish.setAvailable(true);

        DishDto dishDto = dishMapper.toDto(dish);

        assertNotNull(dishDto);
        assertEquals(dishDto.getId(), dish.getId());
        assertEquals(dishDto.getName(), dish.getName());
        assertEquals(dishDto.getDescription(), dish.getDescription());
        assertEquals(dishDto.getCategory(), dish.getCategory());
        assertEquals(dishDto.getPrice(), dish.getPrice());
        assertEquals(dishDto.isAvailable(), dish.isAvailable());
    }

    @Test
    void testToDtoWhenNullEntityProvidedThenNullReturned() {

        DishDto dishDto = dishMapper.toDto(null);

        assertNull(dishDto);
    }

    @Test
    void testToEntityWhenDtoProvidedThenEntityReturned() {

        DishDto dishDto = new DishDto();
        dishDto.setId(1L);
        dishDto.setName("Test Dish");
        dishDto.setDescription("Test Description");
        dishDto.setCategory("Test Category");
        dishDto.setPrice(10.0);
        dishDto.setAvailable(true);

        Dish dish = dishMapper.toEntity(dishDto);

        assertNotNull(dish);
        assertEquals(dish.getId(), dishDto.getId());
        assertEquals(dish.getName(), dishDto.getName());
        assertEquals(dish.getDescription(), dishDto.getDescription());
        assertEquals(dish.getCategory(), dishDto.getCategory());
        assertEquals(dish.getPrice(), dishDto.getPrice());
        assertEquals(dish.isAvailable(), dishDto.isAvailable());
    }

    @Test
    void testToEntityWhenNullDtoProvidedThenNullReturned() {

        Dish dish = dishMapper.toEntity(null);

        assertNull(dish);
    }
}