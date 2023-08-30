package com.gerasimov.capstone.dbclasses.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import lombok.Data;

@Data
public class User {
    private final Long id;
    private final FullName fullName;
    private final String email;
    private final String username;
    private final String password;
    private final String role;
    private final boolean isActive;



    @JsonCreator
    public User(@JsonProperty("id") final Long id,
                @JsonProperty("fullName") final FullName fullName,
                @JsonProperty("email") final String email,
                @JsonProperty("username") final String username,
                @JsonProperty("password") final String password,
                @JsonProperty("role") final String role,
                @JsonProperty("isActive") final boolean isActive) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }

    public User(){
        this.id = null;
        this.fullName = null;
        this.email = null;
        this.username = null;
        this.password = null;
        this.role = null;
        this.isActive = true;
    }

    public User withFullName(String firstName, String lastName) {
        FullName fullName = new FullName(firstName, lastName);
        return new User(
                this.id,
                fullName,
                this.email,
                this.username,
                this.password,
                this.role,
                this.isActive
        );
    }

}
