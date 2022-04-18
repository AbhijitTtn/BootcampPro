package com.ecommerce.bootcampecommerce.controller;

import com.ecommerce.bootcampecommerce.customexception.JsonFormatExceptionClass;
import com.ecommerce.bootcampecommerce.dto.CustomerDTO;

import com.ecommerce.bootcampecommerce.request.EmailRequest;
import com.ecommerce.bootcampecommerce.service.CustomerService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequestMapping("register/customer")
public class RegistrationCustomerController {

    @Autowired
    CustomerService customerService;

    private static final Gson gson = new Gson();


    @PostMapping
    public void saveCustomer(@Valid @RequestBody CustomerDTO customerDTO
            , HttpServletRequest request, HttpServletResponse response) throws Exception{
        CustomerDTO customerDTO1 = customerService.saveCustomer(customerDTO);
        JsonFormatExceptionClass.getJsonMessage("customer successfully registered",request,response);
    }


    @PutMapping("/confirm")
    public String confirmCustomerToken(@Param("token") String token){
        return customerService.confirmToken(token);
    }


    @PostMapping("/resendToken")
    public String resendToken(@Valid @RequestBody EmailRequest email) {
        String email1 = email.getEmail();
        return customerService.checkEmail(email1);
    }
}
