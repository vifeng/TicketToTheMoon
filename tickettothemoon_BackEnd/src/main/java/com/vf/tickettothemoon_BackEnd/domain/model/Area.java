package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Area is a class that represents an area in a venue.
 */
@Entity
public class Area implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int remaining_capacity;

    @ManyToOne
    @JoinColumn(name = "ConfigurationHall_FK")
    private ConfigurationHall configurationHall;

    public Area() {}

    public Area(Long id, String name, int remaining_capacity, ConfigurationHall configurationHall) {
        setId(id);
        setName(name);
        setRemaining_capacity(remaining_capacity);
        setConfigurationHall(configurationHall);
    }

    public Area(String name, int remaining_capacity, ConfigurationHall configurationHall) {
        setName(name);
        setRemaining_capacity(remaining_capacity);
        setConfigurationHall(configurationHall);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRemaining_capacity() {
        return remaining_capacity;
    }

    public void setRemaining_capacity(int remaining_capacity) {
        this.remaining_capacity = remaining_capacity;
    }

    public ConfigurationHall getConfigurationHall() {
        return configurationHall;
    }

    public void setConfigurationHall(ConfigurationHall configurationHall) {
        this.configurationHall = configurationHall;
    }

    @Override
    public String toString() {
        return "Area [id=" + id + ", name=" + name + ", remaining_capacity=" + remaining_capacity
                + ", configurationHall=" + configurationHall + "]";
    }



}
