package com.gerasimov.capstone.dbclasses.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "USERNAME")
    private String userName;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "ROLE_ID")
    private Long roleId;
    @Column(name = "IS_ACTIVE")
    private boolean isActive = true;

    public UserEntity(){

    }

    public UserEntity(String firstName, String lastName, String email, String userName, String password, Long roleId, boolean isActive){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.roleId = roleId;
        this.isActive = isActive;
    }

}
