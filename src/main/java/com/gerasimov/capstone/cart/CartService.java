package com.gerasimov.capstone.cart;

import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.domain.OrderItemDto;
import com.gerasimov.capstone.service.DishService;
import com.gerasimov.capstone.service.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {
    private DishService dishService;
    private Cart cart;
    private OrderItemService orderItemService;
    public void addToCart(Long dishId) {
        DishDto dishDto = dishService.findById(dishId);
        Optional<OrderItemDto> existingItem = findExistingItemInCart(dishDto);

        if (existingItem.isPresent()) {
            increaseQuantityOfExistingCartItem(existingItem.get());
        } else {
            addNewCartItem(dishDto);
        }
    }

    public void removeFromCart(Long dishId) {
        DishDto dishDto = dishService.findById(dishId);
        Optional<OrderItemDto> existingItem = findExistingItemInCart(dishDto);

        if (existingItem.isPresent()) {
            decreaseQuantityOfExistingCartItem(existingItem.get());
        } else {

        }
    }


//    public int findQuantityByDishId(Long dishId){
//        DishDto dishDto = dishService.findById(dishId);
//        OrderItemDto orderItemDto = orderItemService.findByDish(dishDto);
//        return orderItemDto.getQuantity();
//    }

    private Optional<OrderItemDto> findExistingItemInCart(DishDto dishDto) {
        return cart.getCart().stream()
                .filter(orderItem -> orderItem.getDish().equals(dishDto))
                .findFirst();
    }

    private void increaseQuantityOfExistingCartItem(OrderItemDto existingItem) {
        existingItem.setQuantity(existingItem.getQuantity() + 1);
    }

    private void addNewCartItem(DishDto dishDto) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setDish(dishDto);
        orderItemDto.setQuantity(1);
        cart.addItem(orderItemDto);
    }

    private void decreaseQuantityOfExistingCartItem(OrderItemDto existingItem) {
        if (existingItem.getQuantity() > 0){
            existingItem.setQuantity(existingItem.getQuantity() - 1);
        }
    }



}
