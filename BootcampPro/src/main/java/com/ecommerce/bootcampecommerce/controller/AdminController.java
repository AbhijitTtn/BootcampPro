package com.ecommerce.bootcampecommerce.controller;

import com.ecommerce.bootcampecommerce.customexception.JsonFormatExceptionClass;
import com.ecommerce.bootcampecommerce.dto.CustomerDTO;
import com.ecommerce.bootcampecommerce.dto.SellerDTO;
import com.ecommerce.bootcampecommerce.entity.Customer;
import com.ecommerce.bootcampecommerce.entity.Seller;
import com.ecommerce.bootcampecommerce.repository.CustomerRepository;
import com.ecommerce.bootcampecommerce.repository.SellerRepository;
import com.ecommerce.bootcampecommerce.service.CustomerService;
import com.ecommerce.bootcampecommerce.service.SellerService;
import com.ecommerce.bootcampecommerce.token.EmailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    CustomerService customerService;

    @Autowired
    SellerService sellerService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmailSendService emailSendService;

    @Autowired
    SellerRepository sellerRepository;


    @GetMapping("/display/customer")
    public List<CustomerDTO> findAllCustomerData(){
        return customerService.getCustomers();
    }

    @GetMapping("/display/seller")
    public List<SellerDTO> findAllSellerData(){
        return sellerService.getSellers();
    }

    @PatchMapping("/activate/customer")
    public void activateCustomer(@RequestParam("id") Long id
            ,HttpServletRequest request, HttpServletResponse response) throws Exception{
        Optional<Customer> user = customerRepository.findByUserId(id);
                ;
        if(!(user.isPresent())){

            JsonFormatExceptionClass.getJsonMessage("customer not found",request,response);
        } else {
            boolean isActive = user.get().getUser().isActive();
            if(isActive){

                JsonFormatExceptionClass.getJsonMessage("customer Already active",request,response);
            }else {
                user.get().getUser().setActive(true);
                customerRepository.save(user.get());
                // Mail is triggered
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(user.get().getUser().getEmail());
                message.setFrom("test@gmail.com");
                message.setSubject("Account Activation");
                message.setText("YOUR ACCOUNT HAS BEEN ACTIVATED");
                emailSendService.sendEmail(message);

                JsonFormatExceptionClass.getJsonMessage("User Activated Sucessfully",request,response);

            }
        }
    }

    @PatchMapping("/deactivate/customer")
    public void deactivateCustomer(@RequestParam("id") Long id
            ,HttpServletRequest request, HttpServletResponse response) throws Exception{
        Optional<Customer> user = customerRepository.findByUserId(id);
        if(!(user.isPresent())){
//
            JsonFormatExceptionClass.getJsonMessage("customer not found",request,response);
        } else {
            boolean isActive = user.get().getUser().isActive();
            if(!isActive){

                JsonFormatExceptionClass.getJsonMessage("Customer Already Deactived",request,response);
            }else {
                user.get().getUser().setActive(false);
                customerRepository.save(user.get());
                // Mail is triggered
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(user.get().getUser().getEmail());
                message.setFrom("test@gmail.com");
                message.setSubject("Account Deactivation");
                message.setText("YOUR ACCOUNT HAS BEEN DEACTIVATED");
                emailSendService.sendEmail(message);

                JsonFormatExceptionClass.getJsonMessage("User Deactivated Sucessfully",request,response);
            }
        }

    }

    @PatchMapping("/activate/seller")
    public void activateSeller(@RequestParam("id") Long id,
                                            HttpServletRequest request, HttpServletResponse response) throws Exception{
        Optional<Seller> user = sellerRepository.findByUserId(id);
                ;
        if(!(user.isPresent())){

            JsonFormatExceptionClass.getJsonMessage("seller not found",request,response);
        } else {
            boolean isActive = user.get().getUser().isActive();
            if(isActive){

                JsonFormatExceptionClass.getJsonMessage("Seller Already active",request,response);
            }else {
                user.get().getUser().setActive(true);
                sellerRepository.save(user.get());
                // Mail is triggered
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(user.get().getUser().getEmail());
                message.setFrom("test@gmail.com");
                message.setSubject("Account Activation");
                message.setText("YOUR ACCOUNT HAS BEEN ACTIVATED");
                emailSendService.sendEmail(message);

                JsonFormatExceptionClass.getJsonMessage("User Activated Sucessfully",request,response);
            }
        }
    }

    @PatchMapping("/deactivate/seller")
    public void deactivateSeller(@RequestParam("id") Long id,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception{
        Optional<Seller> user = sellerRepository.findByUserId(id);
        if(!(user.isPresent())){
            JsonFormatExceptionClass.getJsonMessage("Seller Not Found",request,response);
        } else {
            boolean isActive = user.get().getUser().isActive();
            if(!isActive){
                JsonFormatExceptionClass.getJsonMessage("Seller Already Deactived",request,response);
            }else {
                user.get().getUser().setActive(false);
                sellerRepository.save(user.get());
                // Mail is triggered
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(user.get().getUser().getEmail());
                message.setFrom("test@gmail.com");
                message.setSubject("Account Deactivation");
                message.setText("YOUR ACCOUNT HAS BEEN DEACTIVATED");
                emailSendService.sendEmail(message);
                JsonFormatExceptionClass.getJsonMessage("User Deactivated Sucessfully",request,response);
            }
        }

    }
}
