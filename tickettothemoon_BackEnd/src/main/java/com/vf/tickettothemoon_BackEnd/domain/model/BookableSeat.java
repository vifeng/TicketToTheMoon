package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * A BookableSeat can be applied to many areas. An area can have many BookableSeats. A BookableSeat
 * is part of a variation. A BookableSeat can be applied to many sessions events. An event session
 * can have many BookableSeats.
 */
@Entity
public class BookableSeat implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private double price;
    private String name;
    private double taxes;

    @ManyToOne
    @JoinColumn(name = "sessionEvents_FK")
    private SessionEvent sessionEvents;

    @ManyToOne
    @JoinColumn(name = "Seat")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "EventOrder_FK")
    private EventOrder eventOrder;

    public BookableSeat() {}

    public BookableSeat(Long id, double price, String name, double taxes,
            SessionEvent sessionEvents, Seat seat, EventOrder eventOrder) {
        setId(id);
        setPrice(price);
        setName(name);
        setTaxes(taxes);
        setSessionEvents(sessionEvents);
        setSeat(seat);
        setEventOrder(eventOrder);
    }

    public BookableSeat(double price, String name, double taxes, SessionEvent sessionEvents,
            Seat seat, EventOrder eventOrder) {
        setPrice(price);
        setName(name);
        setTaxes(taxes);
        setSessionEvents(sessionEvents);
        setSeat(seat);
        setEventOrder(eventOrder);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTaxes() {
        return taxes;
    }

    public void setTaxes(double taxes) {
        this.taxes = taxes;
    }

    public SessionEvent getSessionEvents() {
        return sessionEvents;
    }

    public void setSessionEvents(SessionEvent sessionEvents) {
        this.sessionEvents = sessionEvents;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public EventOrder getEventOrder() {
        return eventOrder;
    }

    public void setEventOrder(EventOrder eventOrder) {
        this.eventOrder = eventOrder;
    }

    @Override
    public String toString() {
        return "BookableSeat [id=" + id + ", price=" + price + ", name=" + name + ", taxes=" + taxes
                + ", sessionEvents=" + sessionEvents + ", seat=" + seat + ", eventOrder="
                + eventOrder + "]";
    }


}
