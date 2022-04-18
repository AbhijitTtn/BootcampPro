package com.ecommerce.bootcampecommerce.controller;

import com.ecommerce.bootcampecommerce.customexception.JsonFormatExceptionClass;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AccessDeniedController {

    @GetMapping("/accessdenied")
    public void getDeniedMessage(HttpServletRequest request, HttpServletResponse response) throws Exception{
        JsonFormatExceptionClass.getJsonMessage("You don't have permission to access this API",request,response);
    }
}
