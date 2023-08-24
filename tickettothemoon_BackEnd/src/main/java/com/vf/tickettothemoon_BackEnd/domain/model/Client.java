package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Client implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String username;
    private String lastName;
    private String email;
    private String phoneNumber;

    @Embedded
    private Address address = new Address();

    // TODO_LOW: a new @embedded class for credit card
    private int creditCardNumber;

    public Client() {}

    public Client(String username) {
        setUsername(username);
    }

    public Client(String username, String firstname, String lastname, Address address) {
        setUsername(username);
        setFirstName(firstname);
        setLastName(lastname);
        setAddress(address);
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(int creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    @Override
    public String toString() {
        return "Client [id=" + id + ", firstName=" + firstName + ", username=" + username
                + ", lastName=" + lastName + ", email=" + email + ", phoneNumber=" + phoneNumber
                + ", address=" + address + ", creditCardNumber=" + creditCardNumber + "]";
    }


}
