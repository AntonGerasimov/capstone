package com.gerasimov.capstone.dbclasses.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Objects;

public class FullAddress {
    private final String street;
    private final String house;
    private final String apartment;

    @JsonCreator
    public FullAddress( @JsonProperty("Street") final String street,
                        @JsonProperty("House") final String house,
                        @JsonProperty("Apartment") final String apartment  ) {
        this.street = street;
        this.house = house;
        this.apartment = apartment;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public String getApartment() {
        return apartment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullAddress that = (FullAddress) o;
        return Objects.equals(street, that.street) && Objects.equals(house, that.house) && Objects.equals(apartment, that.apartment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, house, apartment);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("Street", street)
                .add("House", house)
                .add("Apartment", apartment)
                .toString();
    }
}
