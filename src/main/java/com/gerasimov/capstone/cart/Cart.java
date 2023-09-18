package com.gerasimov.capstone.cart;

import com.gerasimov.capstone.domain.DishDto;
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
    public boolean isContainsDish(DishDto dishDto){
        return items.stream()
                .anyMatch(orderItem -> orderItem.getDish().equals(dishDto));
    }

    public void addDish(DishDto dishDto){
        boolean found = false;
        for (OrderItemDto orderItem : items){
            if (orderItem.getDish().equals(dishDto)){
                orderItem.setQuantity(orderItem.getQuantity() + 1);
                found = true;
                break;
            }
        }
        if (!found){ //this dish adds for the first time
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setDish(dishDto);
            orderItemDto.setQuantity(1);
        }
    }

    public void removeDish(DishDto dishDto){
        boolean found = false;
        for (OrderItemDto orderItem : items){
            if (orderItem.getDish().equals(dishDto)){
                orderItem.setQuantity(orderItem.getQuantity() - 1);
                if (orderItem.getQuantity() == 0){
                    items.remove(orderItem);
                }
                found = true;
                break;
            }
        }
        if (!found){ //attempt to remove dish that is not in the cart yet

        }
    }
}

