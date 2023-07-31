package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * A hall can have different configurations. Each configuration has an area which point to a
 * category containing seats.
 */
@Entity
public class ConfigurationHall implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int configuration_capacity;

    @ManyToOne
    @JoinColumn(name = "Hall_FK")
    private Hall hall;

    public ConfigurationHall() {}

    public ConfigurationHall(Long id, String name, int configuration_capacity, Hall hall) {
        setId(id);
        setName(name);
        setConfiguration_capacity(configuration_capacity);
        setHall(hall);
    }

    public ConfigurationHall(String name, int configuration_capacity, Hall hall) {
        setName(name);
        setConfiguration_capacity(configuration_capacity);
        setHall(hall);
    }

    private void setId(Long id) {
        this.id = id;
    }

    private Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getConfiguration_capacity() {
        return configuration_capacity;
    }

    public void setConfiguration_capacity(int configuration_capacity) {
        this.configuration_capacity = configuration_capacity;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    @Override
    public String toString() {
        return "ConfigurationHall [id=" + id + ", name=" + name + ", configuration_capacity="
                + configuration_capacity + ", hall=" + hall + "]";
    }


}
