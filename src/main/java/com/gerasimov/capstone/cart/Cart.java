package com.gerasimov.capstone.cart;

import com.gerasimov.capstone.domain.OrderItemDto;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<OrderItemDto> items = new ArrayList<>();

    public void addItem(OrderItemDto itemDto) {
        items.add(itemDto);
    }
    public void removeItem(OrderItemDto itemDto) {
        items.remove(itemDto);
    }
    public List<OrderItemDto> getItems() {
        return items;
    }

}

