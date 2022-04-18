package com.ecommerce.bootcampecommerce.customexception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        ErrorModel error = new ErrorModel(HttpStatus.NOT_ACCEPTABLE, "Validation Error", ex.getBindingResult().toString());

        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ErrorModel> handleEntityNotFound(EntityNotFoundException ex){
        ErrorModel error = new ErrorModel(HttpStatus.NOT_FOUND, "Entity not found", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyEsixt.class)
    private ResponseEntity<Object> handleUserNot(UserAlreadyEsixt ex){
        Map<String ,Object> maps = new HashMap<>();
        maps.put("message", ex.getMessage());
        return new ResponseEntity<>(maps,HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(PasswordAndConfirmPasswordMismatchException.class)
    private ResponseEntity<Object> handleUserNot(PasswordAndConfirmPasswordMismatchException ex){
        Map<String ,Object> maps = new HashMap<>();
        maps.put("message", ex.getMessage());
        return new ResponseEntity<>(maps,HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    private ResponseEntity<Object> handleUserNot(CustomerNotFoundException ex){
        Map<String ,Object> maps = new HashMap<>();
        maps.put("message", ex.getMessage());
        return new ResponseEntity<>(maps,HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(UserNotActiveException.class)
    private ResponseEntity<Object> handleUserNot(UserNotActiveException ex){
        Map<String ,Object> maps = new HashMap<>();
        maps.put("message", ex.getMessage());
        return new ResponseEntity<>(maps,HttpStatus.ACCEPTED);
    }


}
