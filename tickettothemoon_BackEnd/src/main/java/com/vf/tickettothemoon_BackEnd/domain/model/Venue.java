package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * A venue has a set of halls. a hall beLongs to one venue. A venue is managed by one or more
 * people.
 */
@Entity
@Table(name = "Venue")
public class Venue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "invalid Name")
    private String name;

    @Embedded
    private Address address = new Address();
    private String contact;
    private String mail;



    public Venue() {}

    public Venue(Long id, String name, String contact, String mail, Address address) {
        setId(id);
        setName(name);
        setContact(contact);
        setMail(mail);
        setAddress(address);
    }

    public Venue(String name, String contact, String mail, Address address) {
        setName(name);
        setContact(contact);
        setMail(mail);
        setAddress(address);
    }


    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "Venue [id=" + id + ", name=" + name + ", address=" + address + ", contact="
                + contact + ", mail=" + mail + "]";
    }



}
