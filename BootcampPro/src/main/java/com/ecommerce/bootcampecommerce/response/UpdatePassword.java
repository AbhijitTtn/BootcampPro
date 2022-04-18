package com.ecommerce.bootcampecommerce.response;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UpdatePassword {

    @Pattern(regexp = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%*!^&+=])"
            + "(?=\\S+$).{8,16}$",
            message = "Password should contain atleast 1 uppercase, 1 lowercase, 1 numeric value and 1 special character")
    @NotBlank(message = "Password field is mandetory")
    private String password;

    @NotBlank(message = "confirm password not blank")
    private String confirmpassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }
}
