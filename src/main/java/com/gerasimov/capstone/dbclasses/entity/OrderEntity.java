package com.gerasimov.capstone.dbclasses.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
public class OrderEntity {
    @Id
    private Long orderId;
    private Long customerId;
    private LocalDateTime orderDatetime;
    private String orderStatus;
    private Long deliveryAddressId;
}
