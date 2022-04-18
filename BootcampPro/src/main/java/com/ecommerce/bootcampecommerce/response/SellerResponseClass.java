package com.ecommerce.bootcampecommerce.response;

import com.ecommerce.bootcampecommerce.dto.AddressDTO;
import com.ecommerce.bootcampecommerce.dto.SellerDTO;
import com.ecommerce.bootcampecommerce.entity.Address;

import java.util.Set;

public class SellerResponseClass {
    private Long id;
    private String firstname;
    private String lastname;
    private String companyname;
    private String companycontact;
    private Boolean isactive;
    private Address address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanycontact() {
        return companycontact;
    }

    public void setCompanycontact(String companycontact) {
        this.companycontact = companycontact;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
