package com.gerasimov.capstone.cart;

import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.domain.OrderItemDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        cart.removeIf(item -> item.getId()==id);
    }
}