package io.khasang.ba.entity;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String region;
    private String city;
    private String street;
    private int postcode;
    private String hause;
    private String office;

    //Geographic coordinates latitude
    @ColumnDefault(value = "0.000000")
    private double latitude;

    //Geographic coordinates longitude
    @ColumnDefault(value = "0.000000")
    private double longitude;

    public Address() {
    }

    public Address(String region, String city, String street, int postcode, String hause) {
        this.region = region;
        this.city = city;
        this.street = street;
        this.postcode = postcode;
        this.hause = hause;
    }

    public Address(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Address(String region, String city, String street, int postcode, String hause, String office,
                   double latitude, double longitude) {
        this.region = region;
        this.city = city;
        this.street = street;
        this.postcode = postcode;
        this.hause = hause;
        this.office = office;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Address(String city, String street, String hause) {
        this.city = city;
        this.street = street;
        this.hause = hause;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getHause() {
        return hause;
    }

    public void setHause(String hause) {
        this.hause = hause;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
