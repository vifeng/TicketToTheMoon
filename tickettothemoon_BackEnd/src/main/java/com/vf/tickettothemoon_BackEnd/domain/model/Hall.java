package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

/**
 * Relation many Hall to one Venue. A hall has a name and a capacityOfHall. A hall can have
 * different configurations.
 */
@Entity
public class Hall implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Hall name cannot be null or blank")
    private String name;
    /**
     * @Description(value = "CapacityOfHall maximum legal of the hall.")
     */
    private int capacityOfHall;

    // TOTEST: cascade options
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            optional = false)
    @JoinColumn(name = "Venue_FK")
    private Venue venue;



    public Hall() {}

    public Hall(Long id, String name, int capacityOfHall, Venue venue) {
        setId(id);
        setName(name);
        setCapacityOfHall(capacityOfHall);
        setVenue(venue);
    }


    public Hall(String name, int capacityOfHall, Venue venue) {
        setName(name);
        setCapacityOfHall(capacityOfHall);
        setVenue(venue);
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
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Hall name cannot be null or blank");
        }
        this.name = name;
    }

    public int getCapacityOfHall() {
        return capacityOfHall;
    }

    public void setCapacityOfHall(int capacityOfHall) {
        this.capacityOfHall = capacityOfHall;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    @Override
    public String toString() {
        return "Hall: [" + name + " CapacityOfHall: " + capacityOfHall + "Venue : " + venue + "]";
    }

}
