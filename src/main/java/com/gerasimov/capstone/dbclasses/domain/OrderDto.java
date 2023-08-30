package com.gerasimov.capstone.dbclasses.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private final Long id;
    private final UserDto customer;
    private final LocalDateTime orderDateTime;
    private final String status;
    private final AddressDto deliveryAddress;

    @JsonCreator
    public OrderDto(@JsonProperty("id") final Long id,
                    @JsonProperty("customer") final UserDto customer,
                    @JsonProperty("OrderDateTime") final LocalDateTime orderDateTime,
                    @JsonProperty("Status") final String status,
                    @JsonProperty("DeliveryAddress") final AddressDto deliveryAddress) {
        this.id = id;
        this.customer = customer;
        this.orderDateTime = orderDateTime;
        this.status = status;
        this.deliveryAddress = deliveryAddress;
    }
}
