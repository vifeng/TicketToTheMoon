package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    /**
     * @Description(value = "this capacity is the actual number of seats available within this
     *                    configuration. exemple: for a Hall of 100, a seated configuration will
     *                    have a capacity of 50. a standing one will have a capacity of 100.")
     */
    private int capacityOfConfiguration;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            optional = false)
    @JoinColumn(name = "Hall_FK")
    private Hall hall;

    public ConfigurationHall() {}

    public ConfigurationHall(Long id, String name, int capacityOfConfiguration, Hall hall) {
        setId(id);
        setName(name);
        setHall(hall);
        setCapacityOfConfiguration(capacityOfConfiguration);
    }

    public ConfigurationHall(String name, int capacityOfConfiguration, Hall hall) {
        setName(name);
        setHall(hall);
        setCapacityOfConfiguration(capacityOfConfiguration);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacityOfConfiguration() {
        return capacityOfConfiguration;
    }

    public void setCapacityOfConfiguration(int capacityOfConfiguration) {
        if (capacityOfConfiguration == 0)
            throw new IllegalArgumentException("capacityOfConfiguration must be positive");
        if (capacityOfConfiguration < 0)
            throw new IllegalArgumentException("capacity must be positive");
        if (capacityOfConfiguration > hall.getCapacityOfHall())
            throw new IllegalArgumentException(
                    "capacity of configuration must be less than capacity of hall");
        this.capacityOfConfiguration = capacityOfConfiguration;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    @Override
    public String toString() {
        return "ConfigurationHall [id=" + id + ", name=" + name + ", capacityOfConfiguration="
                + capacityOfConfiguration + ", hall=" + hall + "]";
    }


}
