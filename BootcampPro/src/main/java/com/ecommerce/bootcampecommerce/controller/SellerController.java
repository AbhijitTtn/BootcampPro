package com.ecommerce.bootcampecommerce.controller;

import com.ecommerce.bootcampecommerce.response.AddressResponse;
import com.ecommerce.bootcampecommerce.response.SellerResponseClass;
import com.ecommerce.bootcampecommerce.response.UpdatePassword;
import com.ecommerce.bootcampecommerce.customexception.JsonFormatExceptionClass;
import com.ecommerce.bootcampecommerce.entity.Address;
import com.ecommerce.bootcampecommerce.entity.Seller;
import com.ecommerce.bootcampecommerce.entity.User;
import com.ecommerce.bootcampecommerce.repository.SellerRepository;
import com.ecommerce.bootcampecommerce.repository.UserRepository;
import com.ecommerce.bootcampecommerce.token.EmailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/seller")
@PreAuthorize("hasRole('SELLER')")
public class SellerController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    EmailSendService emailSendService;

    //API to view Seller Profile(Seller-1)
    @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/myprofile")
    public ResponseEntity<?> getSellerProfile(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username);
        Seller seller = sellerRepository.findByUserId(user.getId()).get();
        SellerResponseClass sellerResponseClass =
                new SellerResponseClass();
        sellerResponseClass.setId(seller.getUser().getId());
        sellerResponseClass.setFirstname(seller.getUser().getFirstName());
        sellerResponseClass.setLastname(seller.getUser().getLastName());
        sellerResponseClass.setIsactive(seller.getUser().isActive());
        sellerResponseClass.setCompanyname(seller.getCompanyName());
        sellerResponseClass.setCompanycontact(seller.getCompanyContact());
        Set<Address> selleraddrs = seller.getUser().getAddresses();
        Address address = new Address();
        selleraddrs.forEach(addr -> {
            address.setCity(addr.getCity());
            address.setState(addr.getState());
            address.setCountry(addr.getCountry());
            address.setAddressline(addr.getAddressline());
            address.setZipcode(addr.getZipcode());
            address.setLabel(addr.getLabel());
        });
        sellerResponseClass.setAddress(address);
        return new ResponseEntity<>(sellerResponseClass, HttpStatus.CREATED);
    }

    //Api to update seller profile(Seller-2)
    @PreAuthorize("hasRole('SELLER')")
    @PatchMapping("/updateprofile")
    public void updateSellerProfile(@RequestBody SellerResponseClass sellerResponseClass
            , HttpServletRequest request, HttpServletResponse response) throws Exception{

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username);
        Seller seller = sellerRepository.findByUserId(user.getId()).get();

        boolean needUpdate = false;

        if (StringUtils.hasLength(sellerResponseClass.getFirstname())) {
            seller.getUser().setFirstName(sellerResponseClass.getFirstname());
            needUpdate = true;
        }

        if (StringUtils.hasLength(sellerResponseClass.getLastname())) {
            seller.getUser().setLastName(sellerResponseClass.getLastname());
            needUpdate = true;
        }

        if (StringUtils.hasLength(sellerResponseClass.getCompanyname())) {
            seller.setCompanyName(sellerResponseClass.getCompanyname());
            needUpdate = true;
        }

        if (StringUtils.hasLength(sellerResponseClass.getCompanycontact())) {
            seller.setCompanyContact(sellerResponseClass.getCompanycontact());
            needUpdate = true;
        }
        //if(needUpdate == true)
            sellerRepository.save(seller);
        JsonFormatExceptionClass.getJsonMessage("Seller profile updated Sucessfully",request,response);
    }

    //Api to update seller password(Seller-3)
    @PreAuthorize("hasRole('SELLER')")
    @PatchMapping("/updatepassword")
    public void updateSellerPassword(@Valid @RequestBody UpdatePassword updatePassword
            , HttpServletRequest request, HttpServletResponse response) throws Exception{
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username);
        Seller seller = sellerRepository.findByUserId(user.getId()).get();
        if(updatePassword.getPassword().equals(updatePassword.getConfirmpassword())){
            user.setPassword(passwordEncoder.encode(updatePassword.getPassword()));
            user.setConfirmPassword(passwordEncoder.encode(updatePassword.getConfirmpassword()));
            seller.setUser(user);
            sellerRepository.save(seller);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setFrom("test@gmail.com");
            message.setSubject("Password Changed");
            message.setText("Password Sucessfully updates");
            emailSendService.sendEmail(message);

            JsonFormatExceptionClass.getJsonMessage("password updated successfully",request,response);
        }else{
            JsonFormatExceptionClass.getJsonMessage("password mismatched",request,response);
        }
    }

    //Api to update Seller Address(Seller-4)
    @PreAuthorize("hasRole('SELLER')")
    @PatchMapping("/updateaddress")
    public void updateSellerAddress(@RequestBody AddressResponse addressResponse
            , HttpServletRequest request, HttpServletResponse response) throws Exception{
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username);
        Seller seller = sellerRepository.findByUserId(user.getId()).get();

        boolean needUpdate = false;
        Set<Address> selleraddrs = seller.getUser().getAddresses();
        Address address = selleraddrs.stream().collect(Collectors.toList()).get(0);
        if (StringUtils.hasLength(addressResponse.getCity())) {
            address.setCity(addressResponse.getCity());
            needUpdate = true;
        }

        if (StringUtils.hasLength(addressResponse.getState())) {
            address.setState(addressResponse.getState());
            needUpdate = true;
        }

        if (StringUtils.hasLength(addressResponse.getCountry())) {
            address.setCountry(addressResponse.getCountry());
            needUpdate = true;
        }

        if (StringUtils.hasLength(addressResponse.getZipcode())) {
            address.setZipcode(addressResponse.getZipcode());
            needUpdate = true;
        }

        if (StringUtils.hasLength(addressResponse.getLabel())) {
            address.setLabel(addressResponse.getLabel());
            needUpdate = true;
        }
        sellerRepository.save(seller);
        JsonFormatExceptionClass.getJsonMessage("Seller address updated Sucessfully" , request,response);

    }

}
