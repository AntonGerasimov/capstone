package com.gerasimov.capstone.cart;

import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.domain.OrderItemDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class Cart {
    private List<OrderItemDto> cart;

    public Cart() {
        this.cart = new ArrayList<>();
    }

    public List<OrderItemDto> getCart() {
        return cart;
    }

    public void addItem(OrderItemDto item) {
        cart.add(item);
    }

    public void removeItem(OrderItemDto item) {
        cart.remove(item);
    }

    public void removeAllItems() {
        cart.clear();
    }

    public void removeItemById(int id) {
        cart.removeIf(item -> item.getId() == id);
    }

    public boolean containsDish(DishDto dishDto) {
        Optional<OrderItemDto> orderItemDtoOptional = getCart().stream()
                .filter(orderItem -> orderItem.getDish() != null && orderItem.getDish().equals(dishDto))
                .findFirst();
        return orderItemDtoOptional.isPresent();

    }

    public int getQuantity(DishDto dishDto) {
        if (dishDto != null && cart != null && !getCart().isEmpty()) {
            Optional<OrderItemDto> orderItemDtoOptional = getCart().stream()
                    .filter(orderItem -> orderItem.getDish() != null && orderItem.getDish().equals(dishDto))
                    .findFirst();
            if (orderItemDtoOptional.isPresent()) {
                return orderItemDtoOptional.get().getQuantity();
            }
        }
        return 0; // Return 0 if cart is null or empty, if dishDto is null, or if the item is not found.
    }
}