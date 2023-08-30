package com.gerasimov.capstone.dbclasses.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ADDRESSES")
@Getter
@Setter
public class AddressEntity {
    @Id
    private Long addressId;
    private Long userId;
    private String street;
    private String house;
    private String apartment;
}


