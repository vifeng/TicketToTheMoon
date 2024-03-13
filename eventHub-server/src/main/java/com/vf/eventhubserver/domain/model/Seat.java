package com.vf.eventhubserver.domain.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Seat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isSeated = false;
    private char rowNo;
    private int seatNo;

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
    private SeatStatus seatStatus;

    public Seat() {}

    /*
     * Constructor with id. for a seated seat with a number and row or standing seat or free
     * isSeated placement
     */
    public Seat(Long id, boolean isSeated, int seatNo, char rowNo, CategorySpatial categorySpatial,
            CategoryTariff categoryTariff, SeatStatus seatStatus,
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
        setSeatStatus(seatStatus);
        setConfigurationHall(configurationHall);
    }

    public Seat(boolean isSeated, int seatNo, char rowNo, CategorySpatial categorySpatial,
            CategoryTariff categoryTariff, SeatStatus seatStatus,
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
        setSeatStatus(seatStatus);
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

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public ConfigurationHall getConfigurationHall() {
        return configurationHall;
    }

    public void setConfigurationHall(ConfigurationHall configurationHall) {
        if (configurationHall == null)
            throw new IllegalArgumentException("ConfigurationHall cannot be null");
        this.configurationHall = configurationHall;
    }

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
                + categorySpatial + ", categoryTariff=" + categoryTariff + ", seatStatus="
                + seatStatus + "]";
    }

}
