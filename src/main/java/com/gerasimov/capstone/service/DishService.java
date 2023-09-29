package com.gerasimov.capstone.service;

import com.gerasimov.capstone.domain.DishDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface DishService {
    List<DishDto> findAll();

    Page<String> findPaginatedMenuCategories(Pageable pageable);

    Map<String, Integer> findCategoryImageMap();

    Page<DishDto> findPaginatedCategoryItems(Pageable pageable, String category, Double minPrice, Double maxPrice);

    List<DishDto> findAvailable();

    Map<String, List<DishDto>> groupDishesByCategory(List<DishDto> dishDtos);

    List<DishDto> findHotSale();

    DishDto findById(Long id);

    List<DishDto> getCartItems(HttpSession httpSession);

    DishDto save(DishDto dishDto, MultipartFile imageFile);

    void update(DishDto dishDto, MultipartFile imageFile);

    void delete(Long dishId);
}
