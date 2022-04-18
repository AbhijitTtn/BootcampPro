package com.ecommerce.bootcampecommerce.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CustomerNotFoundException extends RuntimeException {
    String message;
    public CustomerNotFoundException(String s) {
        this.message=s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
