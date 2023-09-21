package com.gerasimov.capstone.service.impl;

import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.entity.Dish;
import com.gerasimov.capstone.entity.User;
import com.gerasimov.capstone.exception.RestaurantException;
import com.gerasimov.capstone.mapper.DishMapper;
import com.gerasimov.capstone.repository.DishRepository;
import com.gerasimov.capstone.service.DishService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
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
    public List<DishDto> findAvailable(){
        List<Dish> dishEntities = dishRepository.findByIsAvailableTrue();
        return dishEntities.stream()
                .map(dishMapper::toDto)
                .toList();
    }

    @Override
    public List<DishDto> findHotSale(){
        List<DishDto> hotSale = new ArrayList<>();
        DishDto dishDto1 = findById(1L);
        hotSale.add(dishDto1);

        DishDto dishDto2 = findById(2L);
        hotSale.add(dishDto2);

        return hotSale;
    }

    @Override
    public DishDto findById(Long id){
        Dish dish = dishRepository.findById(id).orElseThrow(()->new RestaurantException(String.format("Can't find dish with id %d in database", id)));
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
    public void addToCart(){

    }

}
