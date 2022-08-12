package com.vf.tickettothemoon.administrators.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Person.
 * <p>Juste pour vérifier si le non-sens d'essayer de coller à la grammaire
 * anglaise dans un langage de programmation a aussi contaminé Spring.</p>
 */
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name, surname;

    private String address;

    public Person() {
    }

    public Person(Long id, String name, String surname, String address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
    }


    public Person(String name, String surname, String address) {
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Person id(Long id) {
        this.id = id;
        return this;
    }

    public Person name(String name) {
        this.name = name;
        return this;
    }

    public Person surname(String surname) {
        this.surname = surname;
        return this;
    }

    public Person address(String address) {
        this.address = address;
        return this;
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }



}