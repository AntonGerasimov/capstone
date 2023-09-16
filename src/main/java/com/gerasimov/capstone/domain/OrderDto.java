package com.gerasimov.capstone.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private UserDto customer;
    private LocalDateTime created;
    private String status;
    private AddressDto deliveryAddress;
}