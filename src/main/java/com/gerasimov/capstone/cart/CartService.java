package com.gerasimov.capstone.cart;

import com.gerasimov.capstone.domain.*;
import com.gerasimov.capstone.exception.RestaurantException;
import com.gerasimov.capstone.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public double calculateTotalPrice() {
        double totalPrice = 0.0;

        for (OrderItemDto orderItem : cart.getCart()) {
            totalPrice += orderItem.getDishPrice() * orderItem.getQuantity();
        }

        return totalPrice;
    }

    public List<OrderItemDto> getCart() {
        return cart.getCart();
    }

    public int getNumberOfItemsInCart(){

        if (getCart()==null){
            return 0;
        }

        return getCart()
                .stream()
                .mapToInt(OrderItemDto::getQuantity)
                .sum();
    }

    public Map<String, Integer> createDishQuantityMap() {
        Map<String, Integer> dishQuantityMap = new HashMap<>();

        for (OrderItemDto orderItemDto : cart.getCart()) {
            String dishName = orderItemDto.getDish().getName();
            int quantity = orderItemDto.getQuantity();

            dishQuantityMap.put(dishName, quantity);
        }

        return dishQuantityMap;
    }

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

        validateRemoveItem(dishDto);

        findExistingItemInCart(dishDto).ifPresent(existingItem -> {
            decreaseQuantityOfExistingCartItem(existingItem);
            if (existingItem.getQuantity() == 0) cart.removeItem(existingItem);
        });
    }

    public List<AddressDto> findAddressesForUser() {
        UserDto authenticatedUser = userService.findAuthenticatedUser();
        return addressService.findAvailableForUser(authenticatedUser);
    }

    public void makeOrder(Long selectedAddressId) {
        OrderDto orderDto = new OrderDto();
        AddressDto deliveryAddress = addressService.findById(selectedAddressId);
        orderDto.setDeliveryAddress(deliveryAddress);
        OrderDto savedOrderDto = orderService.save(orderDto);
        saveCartItems(savedOrderDto);
    }

    public void saveCartItems(OrderDto savedOrderDto) {
        cart.getCart().forEach(orderItem -> {
            orderItem.setOrder(savedOrderDto);
            orderItemService.save(orderItem);
        });
    }

    public void clearCart() {
        cart = new Cart();
    }

    private Optional<OrderItemDto> findExistingItemInCart(DishDto dishDto) {
        return cart.getCart().stream()
                .filter(orderItem -> orderItem.getDish().equals(dishDto))
                .findFirst();
    }

    private void increaseQuantityOfExistingCartItem(OrderItemDto existingItem) {
        existingItem.setQuantity(existingItem.getQuantity() + 1);
    }

    private void decreaseQuantityOfExistingCartItem(OrderItemDto existingItem) {
        existingItem.setQuantity(existingItem.getQuantity() - 1);
    }

    private void addNewCartItem(DishDto dishDto) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setDish(dishDto);
        orderItemDto.setQuantity(1);
        orderItemDto.setDishPrice(dishDto.getPrice());
        cart.addItem(orderItemDto);
    }

    private void validateRemoveItem(DishDto dishDto){
        if (findExistingItemInCart(dishDto).isEmpty()) {
            throw new RestaurantException("Attempt to remove item that is not in the cart");
        }
    }

}
