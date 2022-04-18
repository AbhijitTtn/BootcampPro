package com.ecommerce.bootcampecommerce.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PasswordAndConfirmPasswordMismatchException extends RuntimeException {
    String message;
    public PasswordAndConfirmPasswordMismatchException(String s) {
        message=s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
