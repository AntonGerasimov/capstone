package com.gerasimov.capstone.dbclasses.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddressDto {
    private final Long addressId;
    private final UserDto user;
    private final FullAddress fullAddress;

    @JsonCreator
    public AddressDto(@JsonProperty("id") final Long addressId,
                      @JsonProperty("user") final UserDto user,
                      @JsonProperty("street") final FullAddress fullAddress) {
        this.addressId = addressId;
        this.user = user;
        this.fullAddress = fullAddress;

    }
}
