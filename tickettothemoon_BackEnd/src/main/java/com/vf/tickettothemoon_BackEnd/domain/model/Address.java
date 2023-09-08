package com.vf.tickettothemoon_BackEnd.domain.model;

import jakarta.persistence.Embeddable;

/**
 * @see Venue
 * @see Customer
 */
@Embeddable
public class Address {

    private String street;
    private String city;
    private String zipcode;
    private String country;

    public Address() {}

    public Address(String street, String city, String zipcode, String country) {
        setStreet(street);
        setCity(city);
        setZipcode(zipcode);
        setCountry(country);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(final String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address [street=" + street + ", city=" + city + ", zipcode=" + zipcode
                + ", country=" + country + "]";
    }


}
