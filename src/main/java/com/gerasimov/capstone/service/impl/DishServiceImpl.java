package com.gerasimov.capstone.service.impl;

import com.gerasimov.capstone.domain.AddressDto;
import com.gerasimov.capstone.domain.AddressDtoLight;
import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.entity.Dish;
import com.gerasimov.capstone.exception.RestaurantException;
import com.gerasimov.capstone.mapper.DishMapper;
import com.gerasimov.capstone.repository.DishRepository;
import com.gerasimov.capstone.service.DishService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    @Override
    public List<DishDto> findAll() {
        List<Dish> dishEntities = dishRepository.findAll();
        return dishEntities.stream()
                .map(dishMapper::toDto)
                .toList();
    }

    @Override
    public Page<String> findPaginatedMenuCategories(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<String> listToView;

        List<String> menuCategories = getCategories();

        if (menuCategories.size() < startItem) {
            listToView = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, menuCategories.size());
            listToView = menuCategories.subList(startItem, toIndex);
        }

        return new PageImpl<String>(listToView, PageRequest.of(currentPage, pageSize), menuCategories.size());
    }

    @Override
    public Page<DishDto> findPaginatedCategoryItems(Pageable pageable, String category) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<DishDto> list;

        List<DishDto> menuItems = findDishByCategory(category);

        if (menuItems.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, menuItems.size());
            list = menuItems.subList(startItem, toIndex);
        }

        return new PageImpl<DishDto>(list, PageRequest.of(currentPage, pageSize), menuItems.size());
    }

    @Override
    public List<DishDto> findAvailable() {
        return dishRepository.findByIsAvailableTrue().stream()
                .map(dishMapper::toDto).toList();
    }

    @Override
    public Map<String, List<DishDto>> groupDishesByCategory(List<DishDto> dishDtos) {
        return dishDtos.stream()
                .collect(Collectors.groupingBy(DishDto::getCategory));
    }

    @Override
    public List<DishDto> findHotSale() {
        List<DishDto> hotSale = new ArrayList<>();
        DishDto dishDto1 = findById(1L);
        hotSale.add(dishDto1);

        DishDto dishDto2 = findById(2L);
        hotSale.add(dishDto2);

        return hotSale;
    }

    @Override
    public DishDto findById(Long id) {
        Dish dish = dishRepository.findById(id).orElseThrow(() -> new RestaurantException(String.format("Can't find dish with id %d in database", id)));
        return dishMapper.toDto(dish);
    }

    @Override
    public List<DishDto> getCartItems(HttpSession session) {
        // Retrieve the cart data from the user's session
        List<DishDto> cartItems = (List<DishDto>) session.getAttribute("cart");

        if (cartItems == null) {
            cartItems = new ArrayList<>();
            session.setAttribute("cart", cartItems);
        }
        return cartItems;
    }

    @Override
    public DishDto save(DishDto dishDto){
        setAvailable(dishDto);
        Dish savedDish = dishRepository.save(dishMapper.toEntity(dishDto));
        return dishMapper.toDto(savedDish);
    }

    @Override
    @Transactional
    public void update(DishDto dishDto) {
        log.info(String.format("Updating dish %s", dishDto.toString()));
        setAvailable(dishDto);
        dishRepository.save(dishMapper.toEntity(dishDto));
    }

    @Override
    @Transactional
    public void delete(Long dishId){
        DishDto dishDto = findById(dishId);
        dishDto.setAvailable(false);
        dishRepository.save(dishMapper.toEntity(dishDto));
        log.info(String.format("Delete dish with id %s", dishId));
    }

    private List<String> getCategories() {
        List<DishDto> menuItems = findAvailable();
        Set<String> uniqueCategories = new HashSet<>();

        for (DishDto menuItem : menuItems) {
            String category = menuItem.getCategory();
            uniqueCategories.add(category);
        }
        return new ArrayList<>(uniqueCategories);
    }

    private List<DishDto> findDishByCategory(String category){
        List<DishDto> menuItems = findAvailable();
        return menuItems.stream()
                .filter(dish -> category.equals(dish.getCategory()))
                .toList();
    }

    private void setAvailable(DishDto dishDto){
        dishDto.setAvailable(true);
    }



}
