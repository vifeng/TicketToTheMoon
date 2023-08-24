package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 *
 */
@Entity
public class Seat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numberOfTheSeat;
    private char rowOfTheSeat;
    private boolean booked = false;
    private boolean seated = false;

    @ManyToOne
    @JoinColumn(name = "Category_FK")
    private CategorySpatial category;

    @ManyToOne
    @JoinColumn(name = "CategoryTariff_FK")
    private CategoryTariff categoryTariff;

    public Seat() {}

    /*
     * Constructor with id. for a seated seat with a number and row or standing seat or free seated
     * placement
     */
    public Seat(Long id, boolean seated, int numberOfTheSeat, char rowOfTheSeat,
            CategorySpatial category, CategoryTariff categoryTariff, boolean booked) {
        setId(id);
        setBooked(booked);
        if (seated) {
            setSeated(true);
            setNumberOfTheSeat(numberOfTheSeat);
            setRowOfTheSeat(rowOfTheSeat);
        } else {
            setSeated(false);
        }
        setCategory(category);
        setCategoryTariff(categoryTariff);
    }


    /*
     * Constructor without id. for a seated seat with a number and row or standing seat or free
     * seated placement
     */
    public Seat(boolean seated, int numberOfTheSeat, char rowOfTheSeat, CategorySpatial category,
            CategoryTariff categoryTariff, boolean booked) {
        setBooked(booked);
        if (seated) {
            setSeated(true);
            setNumberOfTheSeat(numberOfTheSeat);
            setRowOfTheSeat(rowOfTheSeat);
        } else {
            setSeated(false);
        }
        setCategory(category);
        setCategoryTariff(categoryTariff);
    }



    public CategoryTariff getCategoryTariff() {
        return categoryTariff;
    }

    public void setCategoryTariff(CategoryTariff categoryTariff) {
        this.categoryTariff = categoryTariff;
    }

    public void setCategory(CategorySpatial category) {
        this.category = category;
    }

    public CategorySpatial getCategory() {
        return category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public boolean isSeated() {
        return seated;
    }

    public void setSeated(boolean seated) {
        this.seated = seated;
    }

    public int getNumberOfTheSeat() {
        return numberOfTheSeat;
    }

    public void setNumberOfTheSeat(int numberOfTheSeat) {
        this.numberOfTheSeat = numberOfTheSeat;
    }

    public char getRowOfTheSeat() {
        return rowOfTheSeat;
    }

    public void setRowOfTheSeat(char rowOfTheSeat) {
        this.rowOfTheSeat = rowOfTheSeat;
    }

    // TODO: hashCode and equals methods to be implemented. Tri selon disponibilité et prix.
    // utiliser Comparator<E> qui permet de faire plusieurs tris.

    @Override
    public String toString() {
        return "Seat [id=" + id + ", numberOfTheSeat=" + numberOfTheSeat + ", rowOfTheSeat="
                + rowOfTheSeat + ", booked=" + booked + ", seated=" + seated + ", category="
                + category + ", categoryTariff=" + categoryTariff + "]";
    }



}
