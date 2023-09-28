package com.gerasimov.capstone.service.impl;

import com.gerasimov.capstone.domain.OrderDto;

import java.util.Comparator;

public class OrderComparator implements Comparator<OrderDto> {
    @Override
    public int compare(OrderDto o1, OrderDto o2){
        int statusComparison = compareStatus(o1.getStatus(), o2.getStatus());

        if (statusComparison != 0) {
            return statusComparison;
        }

        return o2.getCreated().compareTo(o1.getCreated());
    }


    private int compareStatus(String status1, String status2) {
        String[] statusOrder = {"Preparing", "Cooking", "Out for Delivery", "Delivered"};

        int index1 = indexOf(statusOrder, status1);
        int index2 = indexOf(statusOrder, status2);

        return Integer.compare(index1, index2);
    }

    private int indexOf(String[] array, String target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return array.length;
    }

}
