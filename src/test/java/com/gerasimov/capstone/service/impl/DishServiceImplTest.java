package com.gerasimov.capstone.service.impl;

import com.gerasimov.capstone.service.impl.DishServiceImpl;
import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.entity.Dish;
import com.gerasimov.capstone.exception.RestaurantException;
import com.gerasimov.capstone.mapper.DishMapper;
import com.gerasimov.capstone.repository.DishRepository;
import com.gerasimov.capstone.specification.DishSpecifications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishServiceImplTest {

    @Mock
    private DishRepository dishRepository;

    @Mock
    private DishMapper dishMapper;

    @InjectMocks
    private DishServiceImpl dishService;

    private Dish dish;
    private DishDto dishDto;
    private Pageable pageable;

    @BeforeEach
    public void setUp() {
        dish = new Dish();
        dish.setId(1L);
        dish.setName("Test Dish");
        dish.setCategory("Test Category");
        dish.setPrice(10.0);
        dish.setAvailable(true);

        dishDto = new DishDto();
        dishDto.setId(1L);
        dishDto.setName("Test Dish");
        dishDto.setCategory("Test Category");
        dishDto.setPrice(10.0);
        dishDto.setAvailable(true);

        pageable = PageRequest.of(0, 5);
    }


    @Test
    void testMakeAvailableWhenDishExistsThenDishIsUpdated() {
        when(dishRepository.findById(dish.getId())).thenReturn(Optional.of(dish));
        dishService.makeAvailable(dish.getId());
        verify(dishRepository, times(1)).findById(dish.getId());
        verify(dishRepository, times(1)).save(dish);
        assertTrue(dish.isAvailable());
    }

    @Test
    void testMakeAvailableWhenDishDoesNotExistThenExceptionIsThrown() {
        when(dishRepository.findById(dish.getId())).thenReturn(Optional.empty());
        Long dishId = dish.getId();
        assertThrows(RestaurantException.class, () -> dishService.makeAvailable(dishId));
        verify(dishRepository, times(1)).findById(dish.getId());
        verify(dishRepository, times(0)).save(any(Dish.class));
    }

    @Test
    void testFindPaginatedCategoryItemsWhenValidInputsThenReturnPageOfDishDto() {
        when(dishRepository.findAll(any(DishSpecifications.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dish), pageable, 1));
        when(dishMapper.toDto(dish)).thenReturn(dishDto);

        Page<DishDto> result = dishService.findPaginatedCategoryItems(pageable, "Test Category", 5.0, 15.0);

        assertTrue(result.getContent().contains(dishDto));
        assertEquals(1, result.getContent().size());
    }

    @Test
    void testFindPaginatedCategoryItemsWhenCategoryDoesNotExistThenReturnEmptyPage() {
        when(dishRepository.findAll(any(DishSpecifications.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList(), pageable, 0));

        Page<DishDto> result = dishService.findPaginatedCategoryItems(pageable, "Nonexistent Category", 5.0, 15.0);

        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void testFindPaginatedCategoryItemsWhenCategoryIsNullThenReturnPageOfDishDto() {
        when(dishRepository.findAll(any(DishSpecifications.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dish), pageable, 1));
        when(dishMapper.toDto(dish)).thenReturn(dishDto);

        Page<DishDto> result = dishService.findPaginatedCategoryItems(pageable, null, 5.0, 15.0);

        assertTrue(result.getContent().contains(dishDto));
        assertEquals(1, result.getTotalElements());
    }
}