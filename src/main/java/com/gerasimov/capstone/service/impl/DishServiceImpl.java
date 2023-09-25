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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
    public List<DishDto> findAvailable() {
        List<Dish> dishEntities = dishRepository.findByIsAvailableTrue();
        return dishEntities.stream()
                .map(dishMapper::toDto)
                .toList();
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

    private void setAvailable(DishDto dishDto){
        dishDto.setAvailable(true);
    }



}
