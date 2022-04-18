package com.ecommerce.bootcampecommerce.controller;

import com.ecommerce.bootcampecommerce.utils.JwtBlacklist;
import com.ecommerce.bootcampecommerce.repository.JwtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/signout")
public class Signout {

    @Autowired
    JwtRepository jwtBlacklistRepository;

    @PostMapping("/customer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public  String logoutCustomer(HttpServletRequest request)
    {
        final String authorizationHeader = request.getHeader("Authorization");
        String jwt = authorizationHeader.substring(7);
        JwtBlacklist jwtBlacklist = new JwtBlacklist();
        jwtBlacklist.setToken(jwt);

        jwtBlacklistRepository.save(jwtBlacklist);
        return "logged out";
    }

    @PostMapping("/seller")
    @PreAuthorize("hasRole('SELLER')")
    public  String logoutSeller(HttpServletRequest request)
    {
        final String authorizationHeader = request.getHeader("Authorization");
        String jwt = authorizationHeader.substring(7);
        JwtBlacklist jwtBlacklist = new JwtBlacklist();
        jwtBlacklist.setToken(jwt);

        jwtBlacklistRepository.save(jwtBlacklist);
        return "logged out";
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public  String logoutAdmin(HttpServletRequest request)
    {
        final String authorizationHeader = request.getHeader("Authorization");
        String jwt = authorizationHeader.substring(7);
        JwtBlacklist jwtBlacklist = new JwtBlacklist();
        jwtBlacklist.setToken(jwt);

        jwtBlacklistRepository.save(jwtBlacklist);
        return "logged out";
    }
}
