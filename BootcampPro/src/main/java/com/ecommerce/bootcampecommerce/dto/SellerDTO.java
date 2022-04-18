package com.ecommerce.bootcampecommerce.dto;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.Set;

public class SellerDTO {

    //10AABCU9603R1Z2
    @Pattern(message = "It should be 15 characters long.\n" +
            "The first 2 characters should be a number.\n" +
            "The next 10 characters should be the PAN number of the taxpayer.\n" +
            "The 13th character (entity code) should be a number from 1-9 or an alphabet.\n" +
            "The 14th character should be Z.\n" +
            "The 15th character should be an alphabet or a number.",regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}"
            + "[A-Z]{1}[1-9A-Z]{1}"
            + "Z[0-9A-Z]{1}$")
    private String GST;
    private String companyContact;
    private String companyName;
    @Valid
    private UserDTO userDTO;
    @Valid
    private Set<AddressDTO> addressDTO;

    public SellerDTO(String GST, String companyContact, String companyName, UserDTO userDTO, Set<AddressDTO> addressDTO) {
        this.GST = GST;
        this.companyContact = companyContact;
        this.companyName = companyName;
        this.userDTO = userDTO;
        this.addressDTO = addressDTO;
    }

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public Set<AddressDTO> getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(Set<AddressDTO> addressDTO) {
        this.addressDTO = addressDTO;
    }
}
