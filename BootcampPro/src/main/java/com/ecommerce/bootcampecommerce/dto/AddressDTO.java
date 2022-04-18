package com.ecommerce.bootcampecommerce.dto;

public class AddressDTO {
    private String city;
    private String state;
    private String country;
    private String addressline;
    private String zipcode;
    private String label;

    public AddressDTO() {
    }

    public AddressDTO(String city, String state, String country, String addressline, String zipcode, String label) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.addressline = addressline;
        this.zipcode = zipcode;
        this.label = label;
    }

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressline() {
        return addressline;
    }

    public void setAddressline(String addressline) {
        this.addressline = addressline;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
