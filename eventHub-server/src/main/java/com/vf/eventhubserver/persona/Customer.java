package com.vf.eventhubserver.persona;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;

@Entity
public class Customer implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;
  private String lastName;
  private String username;
  private String email;
  private String phoneNumber;

  @Embedded private Address address = new Address();

  private String creditCardNumber;

  public Customer() {}

  public Customer(
      Long id,
      String firstName,
      String lastName,
      String username,
      String email,
      String phoneNumber,
      Address address,
      String creditCardNumber) {
    setId(id);
    setFirstName(firstName);
    setLastName(lastName);
    setUsername(username);
    setEmail(email);
    setPhoneNumber(phoneNumber);
    setAddress(address);
    setCreditCardNumber(creditCardNumber);
  }

  public Customer(
      String firstName,
      String lastName,
      String username,
      String email,
      String phoneNumber,
      Address address,
      String creditCardNumber) {
    setFirstName(firstName);
    setLastName(lastName);
    setUsername(username);
    setEmail(email);
    setPhoneNumber(phoneNumber);
    setAddress(address);
    setCreditCardNumber(creditCardNumber);
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

  public String getCreditCardNumber() {
    return creditCardNumber;
  }

  public void setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  @Override
  public String toString() {
    return "Customer [id="
        + id
        + ", firstName="
        + firstName
        + ", username="
        + username
        + ", lastName="
        + lastName
        + ", email="
        + email
        + ", phoneNumber="
        + phoneNumber
        + ", address="
        + address
        + ", creditCardNumber="
        + creditCardNumber
        + "]";
  }
}
