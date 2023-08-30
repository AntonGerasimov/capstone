package com.gerasimov.capstone.dbclasses.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {
    private final Long id;
    private final User customer;
    private final LocalDateTime orderDateTime;
    private final String status;
    private final Address deliveryAddress;

    @JsonCreator
    public Order(@JsonProperty("id") final Long id,
                @JsonProperty("customer") final User customer,
                @JsonProperty("OrderDateTime") final LocalDateTime orderDateTime,
                @JsonProperty("Status") final String status,
                @JsonProperty("DeliveryAddress") final Address deliveryAddress) {
        this.id = id;
        this.customer = customer;
        this.orderDateTime = orderDateTime;
        this.status = status;
        this.deliveryAddress = deliveryAddress;
    }
}
