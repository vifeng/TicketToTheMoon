package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Relation many Hall to one Venue. A hall has a name and a capacity. A hall can have different
 * configurations.
 */
@Entity
public class Hall implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    /**
     * @Description(value = "Capacity maximum legal of the hall.")
     */
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "Venue_FK")
    private Venue venue;



    public Hall() {}

    public Hall(Long id, String name, int capacity, Venue venue) {
        setId(id);
        setName(name);
        setCapacity(capacity);
        setVenue(venue);
    }


    public Hall(String name, int capacity, Venue venue) {
        setName(name);
        setCapacity(capacity);
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
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    @Override
    public String toString() {
        return "Hall: [" + name + " Capacity: " + capacity + "Venue : " + venue + "]";
    }

}
