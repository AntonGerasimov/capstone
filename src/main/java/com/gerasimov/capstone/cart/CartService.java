package com.gerasimov.capstone.cart;

import com.gerasimov.capstone.domain.*;
import com.gerasimov.capstone.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CartService {
    private DishService dishService;
    private Cart cart;
    private OrderItemService orderItemService;
    private UserService userService;
    private AddressService addressService;
    private OrderService orderService;
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

    public List<AddressDto> findAddressesForUser(){
        UserDto authenticatedUser = userService.findAuthenticatedUser();
        return addressService.findAvailableForUser(authenticatedUser);
    }

    public void makeOrder(Long selectedAddressId){
        OrderDto orderDto = new OrderDto();
        AddressDto deliveryAddress = addressService.findById(selectedAddressId);
        orderDto.setDeliveryAddress(deliveryAddress);
        OrderDto savedOrderDto = orderService.save(orderDto);
        saveCartItems(savedOrderDto);
    }

    public void saveCartItems(OrderDto savedOrderDto){
        cart.getCart().forEach(orderItem -> {
            orderItem.setOrder(savedOrderDto);
            orderItemService.save(orderItem);
        });
    }

    public String makeRedirectAfterSuccessOrder(){
        Long userId = userService.findAuthenticatedUser().getId();
        return String.format("redirect:/users/%d/personal-account", userId);
    }

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
