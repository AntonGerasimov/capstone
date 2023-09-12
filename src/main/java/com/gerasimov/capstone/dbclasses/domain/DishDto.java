package com.gerasimov.capstone.dbclasses.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishDto {
    private Long id;
    private String name;
    private String description;
    private String category;
    private Double price;
    private boolean isAvailable;
}
