package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * 
 */
@Entity
public class BookableSeat implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sessionEvent_FK")
    private SessionEvent sessionEvent;
    // TOCHECK: relation
    @ManyToOne
    @JoinColumn(name = "Seat")
    Set<Seat> seats = new TreeSet<Seat>();

    @ManyToOne
    @JoinColumn(name = "EventOrder_FK")
    private EventOrder eventOrder;

    public BookableSeat() {}

    public BookableSeat(Long id, SessionEvent sessionEvent, Seat seat, EventOrder eventOrder) {
        setId(id);
        setSessionEvent(sessionEvent);
        setSeat(seat);
        setEventOrder(eventOrder);
    }

    public BookableSeat(SessionEvent sessionEvent, Seat seat, EventOrder eventOrder) {
        setSessionEvent(sessionEvent);
        setSeat(seat);
        setEventOrder(eventOrder);
    }

    ////////////////////
    // Utilities methods
    ////////////////////

    public List<Seat> createBundleStandingSeats(int startNumero, char rowSeat,
            CategoryTariff categorieTariff, CategorySpatial categorieSpatial, int quantity) {
        List<Seat> bundle = new ArrayList<>();
        for (int i = startNumero; i < quantity; i++) {
            bundle.add(new Seat(false, 0, '\0', categorieSpatial, categorieTariff, false));
        }
        return bundle;
    }

    public List<Seat> createBundleNumerotedSeats(int startNumero, char rowSeat,
            CategoryTariff categorieTariff, CategorySpatial categorieSpatial, int quantity) {
        List<Seat> bundle = new ArrayList<>();
        for (int i = startNumero; i < quantity; i++) {
            bundle.add(new Seat(true, i, rowSeat, categorieSpatial, categorieTariff, false));
        }
        return bundle;
    }

    ///////////////////////
    // Getters, Setters and toString
    ///////////////////////
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SessionEvent getSessionEvent() {
        return sessionEvent;
    }

    public void setSessionEvent(SessionEvent sessionEvent) {
        this.sessionEvent = sessionEvent;
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
        return "BookableSeat [id=" + id + ", bookableSeat=" + bookableSeat + ", sessionEvent="
                + sessionEvent + ", seat=" + seat + ", eventOrder=" + eventOrder + "]";
    }



}
