package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Price implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double priceht;



    @ManyToOne
    @JoinColumn(name = "Event_FK")
    private Event event;


    public Price() {}

    public Price(Long id, double priceht, Event event) {
        setId(id);
        setPriceht(priceht);
        setEvent(event);
    }

    public Price(double priceht, Event event) {
        setPriceht(priceht);
        setEvent(event);
    }

    ////////////////////
    // Utilities methods
    ////////////////////

    ////////////////////
    // Getters & Setters
    ////////////////////

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public double getPriceht() {
        return priceht;
    }

    public void setPriceht(double priceht) {
        this.priceht = priceht;
    }


    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Price [id=" + id + ", priceht=" + priceht + ", event=" + event + "]";
    }



}
