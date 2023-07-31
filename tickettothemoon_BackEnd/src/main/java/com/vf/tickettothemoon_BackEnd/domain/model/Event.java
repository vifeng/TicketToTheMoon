package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.vf.tickettothemoon_BackEnd.myUtil.Day;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * An event is a show, a concert, a movie, a play, etc. It has a name, a start date, an end date and
 * is composed of sessions. It has one configuration hall.
 */
@Entity
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date dateStart;
    private Date dateEnd;
    private Set<Day> closedDay;

    @ManyToOne
    @JoinColumn(name = "ConfigurationHall_FK")
    private ConfigurationHall configurationHall;

    public Event() {}

    public Event(Long id, String name, Date dateStart, Date dateEnd,
            ConfigurationHall configurationHall) {
        setId(id);
        setName(name);
        setDateStart(dateStart);
        setDateEnd(dateEnd);
        closedDay = new HashSet<Day>();
        setConfigurationHall(configurationHall);
    }


    public Event(String name, Date dateStart, Date dateEnd, ConfigurationHall configurationHall) {
        setName(name);
        setDateStart(dateStart);
        setDateEnd(dateEnd);
        closedDay = new HashSet<Day>();
        setConfigurationHall(configurationHall);
    }

    private void setId(Long id) {
        this.id = id;
    }

    private void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    private void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    private void setName(String name) {
        this.name = name;
    }

    public void setClosedDay(Day closedDay) {
        this.closedDay.add(closedDay);
    }

    private void setConfigurationHall(ConfigurationHall configurationHall) {
        this.configurationHall = configurationHall;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public Set<Day> getClosedDay() {
        return closedDay;
    }

    private ConfigurationHall getConfigurationHall() {
        return configurationHall;
    }

    @Override
    public String toString() {
        return "Event: [" + name + " DateStart: " + dateStart + " DateEnd: " + dateEnd
                + " ClosedDay: " + closedDay + "configurationHall : " + configurationHall + "]";
    }

}
