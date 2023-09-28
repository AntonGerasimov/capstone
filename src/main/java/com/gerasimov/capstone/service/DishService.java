package com.gerasimov.capstone.service;

import com.gerasimov.capstone.domain.DishDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface DishService {
    List<DishDto> findAll();

//    Page<DishDto> findPage(int pageNumber);

    Page<String> findPaginatedMenuCategories(Pageable pageable);

    Page<DishDto> findPaginatedCategoryItems(Pageable pageable, String category);

    List<DishDto> findAvailable();

    Map<String, List<DishDto>> groupDishesByCategory(List<DishDto> dishDtos);

    List<DishDto> findHotSale();

    DishDto findById(Long id);

    List<DishDto> getCartItems(HttpSession httpSession);

    DishDto save(DishDto dishDto);

    void update(DishDto dishDto);

    void delete(Long dishId);
}
