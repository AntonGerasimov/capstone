package com.gerasimov.capstone.mapper;

import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.entity.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


class DishMapperTest {

    private DishMapper dishMapper = DishMapper.INSTANCE;

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
}