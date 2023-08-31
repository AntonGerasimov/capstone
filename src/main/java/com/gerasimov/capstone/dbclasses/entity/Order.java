package com.gerasimov.capstone.dbclasses.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    private Long orderId;
    private Long customerId;
    private LocalDateTime orderDatetime;
    private String orderStatus;
    private Long deliveryAddressId;
}
