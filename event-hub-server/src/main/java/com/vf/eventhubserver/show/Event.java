package com.vf.eventhubserver.show;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Event implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private LocalDate dateStart;
  private LocalDate dateEnd;
  private String closedDay;

  public Event() {}

  public Event(
      Long id,
      String name,
      String description,
      LocalDate dateStart,
      LocalDate dateEnd,
      String closedDay) {
    setId(id);
    setName(name);
    setDescription(description);
    setDateStart(dateStart);
    setDateEnd(dateEnd);
    setClosedDay(closedDay);
  }

  public Event(
      String name, String description, LocalDate dateStart, LocalDate dateEnd, String closedDay) {
    setName(name);
    setDescription(description);
    setDateStart(dateStart);
    setDateEnd(dateEnd);
    setClosedDay(closedDay);
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
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getDateStart() {
    return dateStart;
  }

  public void setDateStart(LocalDate dateStart) {
    this.dateStart = dateStart;
  }

  public LocalDate getDateEnd() {
    return dateEnd;
  }

  public void setDateEnd(LocalDate dateEnd) {
    this.dateEnd = dateEnd;
  }

  public String getClosedDay() {
    return closedDay;
  }

  public void setClosedDay(String closedDay) {
    this.closedDay = closedDay;
  }

  @Override
  public String toString() {
    return "Event [id="
        + id
        + ", name="
        + name
        + ", description="
        + description
        + ", dateStart="
        + dateStart
        + ", dateEnd="
        + dateEnd
        + ", closedDay="
        + closedDay
        + "]";
  }
}
