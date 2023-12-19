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
    private boolean isSeated = false;
    private char rowNo;
    private int seatNo;
    // relationships
    @ManyToOne
    @JoinColumn(name = "ConfigurationHall_FK")
    private ConfigurationHall configurationHall;

    @ManyToOne
    @JoinColumn(name = "CategorySpatial_FK")
    private CategorySpatial categorySpatial;

    @ManyToOne
    @JoinColumn(name = "CategoryTariff_FK")
    private CategoryTariff categoryTariff;

    @ManyToOne
    @JoinColumn(name = "Seat_Status_FK")
    private Seat_Status seat_Status;


    public Seat() {}

    /*
     * Constructor with id. for a seated seat with a number and row or standing seat or free
     * isSeated placement
     */
    public Seat(Long id, boolean isSeated, int seatNo, char rowNo, CategorySpatial categorySpatial,
            CategoryTariff categoryTariff, Seat_Status seat_status,
            ConfigurationHall configurationHall) {
        setId(id);
        if (isSeated) {
            setIsSeated(isSeated);
            setSeatNo(seatNo);
            setRowNo(rowNo);
        } else {
            setIsSeated(false);
        }
        setCategorySpatial(categorySpatial);
        setCategoryTariff(categoryTariff);
        setSeat_Status(seat_status);
        setConfigurationHall(configurationHall);
    }


    /*
     * Constructor without id.
     */
    public Seat(boolean isSeated, int seatNo, char rowNo, CategorySpatial categorySpatial,
            CategoryTariff categoryTariff, Seat_Status seat_status,
            ConfigurationHall configurationHall) {
        if (isSeated) {
            setIsSeated(isSeated);
            setSeatNo(seatNo);
            setRowNo(rowNo);
        } else {
            setIsSeated(false);
        }
        setCategorySpatial(categorySpatial);
        setCategoryTariff(categoryTariff);
        setSeat_Status(seat_status);
        setConfigurationHall(configurationHall);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsSeated() {
        return isSeated;
    }

    public void setIsSeated(boolean isSeated) {
        this.isSeated = isSeated;
    }

    public char getRowNo() {
        return rowNo;
    }

    public void setRowNo(char rowNo) {
        this.rowNo = rowNo;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }



    ///////////////////
    // RELATIONSHIPS //
    ///////////////////

    public CategorySpatial getCategorySpatial() {
        return categorySpatial;
    }

    public void setCategorySpatial(CategorySpatial categorySpatial) {
        this.categorySpatial = categorySpatial;
    }

    public CategoryTariff getCategoryTariff() {
        return categoryTariff;
    }

    public void setCategoryTariff(CategoryTariff categoryTariff) {
        this.categoryTariff = categoryTariff;
    }

    public Seat_Status getSeat_Status() {
        return seat_Status;
    }

    public void setSeat_Status(Seat_Status seat_Status) {
        this.seat_Status = seat_Status;
    }

    public ConfigurationHall getConfigurationHall() {
        return configurationHall;
    }

    public void setConfigurationHall(ConfigurationHall configurationHall) {
        if (configurationHall == null)
            throw new IllegalArgumentException("ConfigurationHall cannot be null");
        this.configurationHall = configurationHall;
    }



    // TODO: Tri selon disponibilité et prix. utiliser Comparator<E> qui permet de faire plusieurs
    // tris.

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Seat other = (Seat) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Seat [id=" + id + ", isSeated=" + isSeated + ", rowNo=" + rowNo + ", seatNo="
                + seatNo + ", configurationHall=" + configurationHall + ", categorySpatial="
                + categorySpatial + ", categoryTariff=" + categoryTariff + ", seat_Status="
                + seat_Status + "]";
    }

}
