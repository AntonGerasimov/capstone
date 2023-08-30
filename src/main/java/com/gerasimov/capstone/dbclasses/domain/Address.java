package com.gerasimov.capstone.dbclasses.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Address {
    private final Long addressId;
    private final User user;
    private final FullAddress fullAddress;

    @JsonCreator
    public Address(@JsonProperty("id") final Long addressId,
                @JsonProperty("user") final User user,
                @JsonProperty("street") final FullAddress fullAddress) {
        this.addressId = addressId;
        this.user = user;
        this.fullAddress = fullAddress;

    }
}
