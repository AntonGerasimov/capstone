package com.gerasimov.capstone.dbclasses.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Dish {
    private final Long dishId;
    private final String dishName;
    private final String description;
    private final double price;
    private final boolean isAvailable;
    @JsonCreator
    public Dish(@JsonProperty("id") final Long dishId,
                   @JsonProperty("name") final String dishName,
                   @JsonProperty("description") final String description,
                   @JsonProperty("price") final double price,
                   @JsonProperty("isAvailable") final boolean isAvailable) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
    }
}
