package com.gerasimov.capstone.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDtoLight {
    private Long id;
    private Long userId;
    private String street;
    private String house;
    private String apartment;
    private boolean isActive = true;
}

