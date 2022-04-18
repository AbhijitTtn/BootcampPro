package com.ecommerce.bootcampecommerce.jwtmodel;


public class AuthenticationResponse {


    private final String jwttoken;


    public AuthenticationResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getJwttoken() {
        return jwttoken;
    }
}
