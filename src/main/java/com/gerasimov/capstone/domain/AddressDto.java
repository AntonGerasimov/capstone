package com.gerasimov.capstone.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private Long id;
    private UserDto user;
    private String street;
    private String house;
    private String apartment;
    private boolean isActive = true;
}
