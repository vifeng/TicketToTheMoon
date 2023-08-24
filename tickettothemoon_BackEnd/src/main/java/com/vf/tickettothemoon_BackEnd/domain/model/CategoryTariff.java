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
public class CategoryTariff implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "ConfigurationHall_FK")
    private ConfigurationHall configurationHall;

    @ManyToOne
    @JoinColumn(name = "Price_FK")
    private Price price;


    public CategoryTariff() {}

    public CategoryTariff(Long id, String name, ConfigurationHall configurationHall, Price price) {
        setId(id);
        setName(name);
        setConfigurationHall(configurationHall);
        setPrice(price);
    }

    public CategoryTariff(String name, ConfigurationHall configurationHall, Price Price) {
        setName(name);
        setConfigurationHall(configurationHall);
        setPrice(price);
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

    public ConfigurationHall getConfigurationHall() {
        return configurationHall;
    }

    public void setConfigurationHall(ConfigurationHall configurationHall) {
        this.configurationHall = configurationHall;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Area [id=" + id + ", name=" + name + ", configurationHall=" + configurationHall
                + "]";
    }

}
