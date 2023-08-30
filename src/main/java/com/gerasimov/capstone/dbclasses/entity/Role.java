package com.gerasimov.capstone.dbclasses.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
    @Id
    private Long id;
    private String roleName;
}
