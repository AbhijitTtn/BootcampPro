package com.ecommerce.bootcampecommerce.controller;

import com.ecommerce.bootcampecommerce.request.EmailRequest;
import com.ecommerce.bootcampecommerce.request.PasswordRequest;
import com.ecommerce.bootcampecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/forgetPassword")
public class ForgetPasswordController {
    @Autowired
    UserService userService;

    @PostMapping
    public String forgetPassword(@Valid @RequestBody EmailRequest email) throws RuntimeException {
        String email1=email.getEmail();
        userService.forgetPasswordSendMail(email1);
        return "check your mail";
    }

    @Transactional
    @PatchMapping("/updatePassword")
    public String updatePassword(@Param("token") String token, @Valid @RequestBody PasswordRequest passwordRequest) {
        return userService.updatePassword(token,passwordRequest);
    }
}
