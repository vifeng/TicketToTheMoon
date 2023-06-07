package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "Venue")
public class Venue implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String zipCode;
    private String town;
    private String contact;
    private String mail;

    protected Venue() {}

    public Venue(Long id, String name, String address, String zipCode, String town, String contact,
            String mail) {
        setId(id);
        setName(name);
        setAddress(address);
        setZipCode(zipCode);
        setTown(town);
        setContact(contact);
        setMail(mail);
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Venue(String name, String address, String zipCode, String town, String contact,
            String mail) {
        setName(name);
        setAddress(address);
        setZipCode(zipCode);
        setTown(town);
        setContact(contact);
        setMail(mail);
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
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
        return "Venue [id=" + id + ", name=" + name + ", address=" + address + ", zipCode="
                + zipCode + ", town=" + town + ", contact=" + contact + ", mail=" + mail + "]";
    }



}
