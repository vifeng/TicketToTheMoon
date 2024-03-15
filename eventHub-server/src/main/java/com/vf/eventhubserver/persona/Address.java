package com.vf.eventhubserver.persona;

import com.vf.eventhubserver.venue.Venue;
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

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address [street=" + street + ", city=" + city + ", zipcode=" + zipcode
                + ", country=" + country + "]";
    }

}
