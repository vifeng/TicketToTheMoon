package com.vf.tickettothemoon.administrators.domain.model;

import javax.persistence.Entity;
import javax.persistence.*;

import lombok.*;


@Entity
@Table(name = "employees")
public @Data class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    private String email;
}
