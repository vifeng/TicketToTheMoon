package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
    private boolean isBooked = false;

    @ManyToOne
    @JoinColumn(name = "CategorySpatial_FK")
    private CategorySpatial categorySpatial;

    @ManyToOne
    @JoinColumn(name = "CategoryTariff_FK")
    private CategoryTariff categoryTariff;

    @ManyToOne
    @JoinColumn(name = "ConfigurationHall_FK")
    private ConfigurationHall configurationHall;

    /**
     * many-to-many Bidirectional child side of the relationship (@mappedBy). We will also use the
     * constraint : cascade property to cascade all operations except REMOVE because we do not want
     * to delete all associated ticket_reservation, if a seat gets deleted.
     * FYI @JsonIgnoreProperties is used to avoid the infinite recursion
     */
    @ManyToMany(mappedBy = "seats",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
                    CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JsonIgnoreProperties("seats")
    private List<Ticket_Reservation> ticket_Reservations = new ArrayList<>();

    public Seat() {}

    /*
     * Constructor with id. for a seated seat with a number and row or standing seat or free
     * isSeated placement
     */
    public Seat(Long id, boolean isSeated, int seatNo, char rowNo, CategorySpatial categorySpatial,
            CategoryTariff categoryTariff, boolean isBooked, ConfigurationHall configurationHall,
            List<Ticket_Reservation> ticket_Reservations) {
        setId(id);
        setIsBooked(isBooked);
        if (isSeated) {
            setIsSeated(true);
            setSeatNo(seatNo);
            setRowNo(rowNo);
        } else {
            setIsSeated(false);
        }
        setCategorySpatial(categorySpatial);
        setCategoryTariff(categoryTariff);
        setConfigurationHall(configurationHall);
        setTicket_Reservations(ticket_Reservations);
    }


    /*
     * Constructor without id, configurationHall and Ticket_Reservation. For a seated seat with a
     * number and row or standing seat or free seated placement
     */
    public Seat(boolean isSeated, int seatNo, char rowNo, CategorySpatial categorySpatial,
            CategoryTariff categoryTariff, boolean isBooked) {
        setIsBooked(isBooked);
        if (isSeated) {
            setIsSeated(true);
            setSeatNo(seatNo);
            setRowNo(rowNo);
        } else {
            setIsSeated(false);
        }
        setCategorySpatial(categorySpatial);
        setCategoryTariff(categoryTariff);
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

    public boolean getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(boolean isBooked) {
        this.isBooked = isBooked;
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

    public ConfigurationHall getConfigurationHall() {
        return configurationHall;
    }

    public void setConfigurationHall(ConfigurationHall configurationHall) {
        this.configurationHall = configurationHall;
    }

    public List<Ticket_Reservation> getTicket_Reservations() {
        return ticket_Reservations;
    }

    public void setTicket_Reservations(List<Ticket_Reservation> ticket_Reservations) {
        this.ticket_Reservations = ticket_Reservations;
    }


    // TODO: hashCode and equals methods to be implemented. Tri selon disponibilité et prix.
    // utiliser Comparator<E> qui permet de faire plusieurs tris.

    @Override
    public String toString() {
        return "Seat [id=" + id + ", isSeated=" + isSeated + ", rowNo=" + rowNo + ", seatNo="
                + seatNo + ", isBooked=" + isBooked + ", categorySpatial=" + categorySpatial
                + ", categoryTariff=" + categoryTariff + ", configurationHall=" + configurationHall
                + "]";
    }

}
