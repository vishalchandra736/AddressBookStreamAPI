package com.blz.addressbookstreamapi;

public class Address {
    String city;
    String state;
    String zip;

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "City : " + city + "\tState : " + state + "\tZip : " + zip;
    }
}
