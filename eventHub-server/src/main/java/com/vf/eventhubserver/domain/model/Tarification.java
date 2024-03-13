package com.vf.eventhubserver.domain.model;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Tarification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double basePrice;
    private double taxeRate;
    private double discountStudentRate;
    private double discountSeniorRate;
    private double discountChildRate;
    private double discountUnemployedRate;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            optional = false)
    @JoinColumn(name = "Event_FK")
    private Event event;

    public Tarification() {}

    public Tarification(Long id, double basePrice, double taxeRate, double discountStudentRate,
            double discountSeniorRate, double discountChildRate, double discountUnemployedRate,
            Event event) {
        setId(id);
        setBasePrice(basePrice);
        setTaxeRate(taxeRate);
        setDiscountStudentRate(discountStudentRate);
        setDiscountSeniorRate(discountSeniorRate);
        setDiscountChildRate(discountChildRate);
        setDiscountUnemployedRate(discountUnemployedRate);
        setEvent(event);
    }

    public Tarification(double basePrice, double taxeRate, double discountStudentRate,
            double discountSeniorRate, double discountChildRate, double discountUnemployedRate,
            Event event) {
        setBasePrice(basePrice);
        setTaxeRate(taxeRate);
        setDiscountStudentRate(discountStudentRate);
        setDiscountSeniorRate(discountSeniorRate);
        setDiscountChildRate(discountChildRate);
        setDiscountUnemployedRate(discountUnemployedRate);
        setEvent(event);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getTaxeRate() {
        return taxeRate;
    }

    public void setTaxeRate(double taxeRate) {
        this.taxeRate = taxeRate;
    }

    public double getDiscountStudentRate() {
        return discountStudentRate;
    }

    public void setDiscountStudentRate(double discountStudentRate) {
        this.discountStudentRate = discountStudentRate;
    }

    public double getDiscountSeniorRate() {
        return discountSeniorRate;
    }

    public void setDiscountSeniorRate(double discountSeniorRate) {
        this.discountSeniorRate = discountSeniorRate;
    }

    public double getDiscountChildRate() {
        return discountChildRate;
    }

    public void setDiscountChildRate(double discountChildRate) {
        this.discountChildRate = discountChildRate;
    }

    public double getDiscountUnemployedRate() {
        return discountUnemployedRate;
    }

    public void setDiscountUnemployedRate(double discountUnemployedRate) {
        this.discountUnemployedRate = discountUnemployedRate;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Tarification [id=" + id + ", basePrice=" + basePrice + ", taxeRate=" + taxeRate
                + ", discountStudentRate=" + discountStudentRate + ", discountSeniorRate="
                + discountSeniorRate + ", discountChildRate=" + discountChildRate
                + ", discountUnemployedRate=" + discountUnemployedRate + ", event=" + event + "]";
    }

}
