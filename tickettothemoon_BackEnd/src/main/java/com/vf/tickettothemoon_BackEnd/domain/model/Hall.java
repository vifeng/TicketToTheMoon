package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * A venue has a set of halls. A hall has a name and a capacity. A hall can have different
 * configurations.
 */
@Entity
public class Hall implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    /**
     * @Description(value = "Capacity of the hall")
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

    private void setId(Long id) {
        this.id = id;
    }


    private void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    private void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public Venue getVenue() {
        return venue;
    }

    private void setVenue(Venue venue) {
        this.venue = venue;
    }

    @Override
    public String toString() {
        return "Hall: [" + name + " Capacity: " + capacity + "Venue : " + venue + "]";
    }

}
