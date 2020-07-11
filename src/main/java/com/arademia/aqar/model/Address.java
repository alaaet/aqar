package com.arademia.aqar.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of ={"firstLine","secondLine","city","region","country"})
public class Address {
    private Integer userId;
    private String firstLine, secondLine, city,region,country,postalCode;

    public Address(Integer userId, String firstLine, String secondLine, String city, String region, String country, String postalCode) {
        this.userId = userId;
        this.firstLine = firstLine;
        this.secondLine = secondLine;
        this.city = city;
        this.region = region;
        this.country = country;
        this.postalCode = postalCode;
    }

    public Address() {
    }

    @Override
    public String toString() {
        return "Address{" +
                "userId=" + userId +
                ", firstLine='" + firstLine + '\'' +
                ", secondLine='" + secondLine + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstLine() {
        return firstLine;
    }

    public void setFirstLine(String firstLine) {
        this.firstLine = firstLine;
    }

    public String getSecondLine() {
        return secondLine;
    }

    public void setSecondLine(String secondLine) {
        this.secondLine = secondLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
