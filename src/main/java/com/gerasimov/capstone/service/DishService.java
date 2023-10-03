package com.gerasimov.capstone.service;

import com.gerasimov.capstone.domain.DishDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface DishService {
    List<DishDto> findAll();


    Page<String> findPaginatedMenuCategories(Pageable pageable);

    Map<String, Integer> findCategoryImageMap();

    Page<DishDto> findPaginatedCategoryItems(Pageable pageable, String category, Double minPrice, Double maxPrice);

    Page<DishDto> findManageMenuPage(Pageable pageable, Boolean isAvailable, double minPrice, double maxPrice);

    Page<DishDto> findByFilter(String category, Boolean isAvailable, double minPrice, double maxPrice, Pageable pageable);

    List<DishDto> findAvailable();

    Map<String, List<DishDto>> groupDishesByCategory(List<DishDto> dishDtos);

    DishDto findById(Long id);

    DishDto save(DishDto dishDto, MultipartFile imageFile);

    void update(DishDto dishDto, MultipartFile imageFile);

    void makeAvailable(Long dishId);

    void delete(Long dishId);
}
