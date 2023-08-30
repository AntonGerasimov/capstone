package com.gerasimov.capstone.dbclasses.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private Long roleId;
    private boolean isActive = true;

    public User(){

    }

    public User(String firstName, String lastName, String email, String username, String password, Long roleId, boolean isActive){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.isActive = isActive;
    }

}
