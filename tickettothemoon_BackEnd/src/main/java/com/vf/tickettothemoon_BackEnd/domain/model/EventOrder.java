package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class EventOrder implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime dateOrder;
    private Date dateSessionEvent;
    private boolean isPaid;
    private int fees;
    private double totalHT;
    private double totalTTC;

    @ManyToOne
    @JoinColumn(name = "Client_FK")
    private Client client;

    public EventOrder() {}

    public EventOrder(Long id, LocalDateTime dateOrder, Date dateSessionEvent, boolean isPaid,
            int fees, double totalHT, double totalTTC, Client client) {
        setId(id);
        setDateOrder(dateOrder);
        setDateSessionEvent(dateSessionEvent);
        setIsPaid(isPaid);
        setFees(fees);
        setTotalHT(totalHT);
        setTotalTTC(totalTTC);
        setClient(client);
    }

    public EventOrder(LocalDateTime dateOrder, Date dateSessionEvent, boolean isPaid, int fees,
            double totalHT, double totalTTC, Client client) {
        setDateOrder(dateOrder);
        setDateSessionEvent(dateSessionEvent);
        setIsPaid(isPaid);
        setFees(fees);
        setTotalHT(totalHT);
        setTotalTTC(totalTTC);
        setClient(client);
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(LocalDateTime dateOrder) {
        this.dateOrder = dateOrder;
    }


    public Date getDateSessionEvent() {
        return dateSessionEvent;
    }

    public void setDateSessionEvent(Date dateSessionEvent) {
        this.dateSessionEvent = dateSessionEvent;
    }

    public boolean isPaid() {
        return isPaid;
    }

    private void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public double getTotalHT() {
        return totalHT;
    }

    public void setTotalHT(double totalHT) {
        this.totalHT = totalHT;
    }

    public double getTotalTTC() {
        return totalTTC;
    }

    public void setTotalTTC(double totalTTC) {
        this.totalTTC = totalTTC;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", dateOrder=" + dateOrder + ", dateSessionEvent="
                + dateSessionEvent + ", isPaid=" + isPaid + ", fees=" + fees + ", totalHT="
                + totalHT + ", totalTTC=" + totalTTC + ", client=" + client + "]";
    }



}
