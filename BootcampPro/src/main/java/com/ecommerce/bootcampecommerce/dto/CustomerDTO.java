package com.ecommerce.bootcampecommerce.dto;
import com.ecommerce.bootcampecommerce.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CustomerDTO {


    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$",
            message = "Phone number not vaild")
    String contactNumber;
    @Valid
    UserDTO userDTO;

    public CustomerDTO(String contactNumber, UserDTO userDTO) {
        this.contactNumber = contactNumber;
        this.userDTO = userDTO;
    }


    public CustomerDTO(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
