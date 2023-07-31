package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * A seat can be reserved by a user.A seat is part of a category which is part of an area which is
 * part of a configuration which is part of a hall which is part of a session event which is part of
 * an event.A seat can be seated or not seated.If a seat is seated,it has a number and a
 * rowSeat.Once the user has reserved a seat,the seat is no Longer available for other users.
 */
@Entity
public class Seat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int number;
    private int rowSeat;
    private boolean reserved = false;
    private boolean seated = false;

    @ManyToOne
    @JoinColumn(name = "Category_FK")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "Area_FK")
    private Area area;

    public Seat() {}

    public Seat(Long id, boolean reserved, boolean seated, int number, int rowSeat,
            Category category, Area area) {
        setId(id);
        setReserved(reserved);
        setSeated(seated);
        setNumber(number);
        setRow(rowSeat);
        setCategory(category);
        setArea(area);
    }


    public Seat(boolean reserved, boolean seated, int number, int rowSeat, Category category,
            Area area) {
        setReserved(reserved);
        setSeated(seated);
        setNumber(number);
        setRow(rowSeat);
        setCategory(category);
        setArea(area);
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    private void setCategory(Category category) {
        this.category = category;
    }

    private Category getCategory() {
        return category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public boolean isSeated() {
        return seated;
    }

    public void setSeated(boolean seated) {
        this.seated = seated;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getRow() {
        return rowSeat;
    }

    public void setRow(int rowSeat) {
        this.rowSeat = rowSeat;
    }

    @Override
    public String toString() {
        return "Seat [id=" + id + ", reserved=" + reserved + ", seated=" + seated + ", number="
                + number + ", rowSeat=" + rowSeat + "]";
    }



}
