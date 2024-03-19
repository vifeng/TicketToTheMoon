package com.vf.eventhubserver.venue;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Hall implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "Hall name cannot be null or blank")
  private String name;

  /**
   * @Description(value = "CapacityOfHall maximum legal of the hall.")
   */
  private int capacityOfHall;

  @ManyToOne(
      cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
      optional = false)
  @JoinColumn(name = "Venue_FK")
  private Venue venue;

  public Hall() {}

  public Hall(Long id, String name, int capacityOfHall, Venue venue) {
    setId(id);
    setName(name);
    setCapacityOfHall(capacityOfHall);
    setVenue(venue);
  }

  public Hall(String name, int capacityOfHall, Venue venue) {
    setName(name);
    setCapacityOfHall(capacityOfHall);
    setVenue(venue);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Hall name cannot be null or blank");
    }
    this.name = name;
  }

  public int getCapacityOfHall() {
    return capacityOfHall;
  }

  public void setCapacityOfHall(int capacityOfHall) {
    if (capacityOfHall == 0) throw new IllegalArgumentException("capacityOfHall must be positive");
    this.capacityOfHall = capacityOfHall;
  }

  public Venue getVenue() {
    return venue;
  }

  public void setVenue(Venue venue) {
    this.venue = venue;
  }

  @Override
  public String toString() {
    return "Hall: [" + name + " CapacityOfHall: " + capacityOfHall + "Venue : " + venue + "]";
  }
}
