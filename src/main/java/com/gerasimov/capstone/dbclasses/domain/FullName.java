package com.gerasimov.capstone.dbclasses.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class FullName {
    private final String firstName;
    private final String lastName;

    @JsonCreator
    public FullName(@JsonProperty("firstName") final String firstName,
                    @JsonProperty("lastName") final String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public FullName(){
        this.firstName = "";
        this.lastName = "";
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FullName fullName = (FullName) o;
        return Objects.equal(firstName, fullName.firstName) &&
                Objects.equal(lastName, fullName.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(firstName, lastName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .toString();
    }
}
