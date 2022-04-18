package com.ecommerce.bootcampecommerce.controller;

import com.ecommerce.bootcampecommerce.response.AddressResponse;
import com.ecommerce.bootcampecommerce.response.CustomerResponseClass;
import com.ecommerce.bootcampecommerce.response.UpdatePassword;
import com.ecommerce.bootcampecommerce.customexception.JsonFormatExceptionClass;
import com.ecommerce.bootcampecommerce.dto.AddressDTO;
import com.ecommerce.bootcampecommerce.entity.Address;
import com.ecommerce.bootcampecommerce.entity.Customer;
import com.ecommerce.bootcampecommerce.entity.User;
import com.ecommerce.bootcampecommerce.repository.AddressRepository;
import com.ecommerce.bootcampecommerce.repository.CustomerRepository;
import com.ecommerce.bootcampecommerce.repository.UserRepository;
import com.ecommerce.bootcampecommerce.service.CustomerService;
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

@RestController
@RequestMapping("/customer")
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailSendService emailSendService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AddressRepository addressRepository;

    //Api to View My Profile(Customer-1)
    @GetMapping(value = "/myprofile")
    public ResponseEntity<CustomerResponseClass>
    getAllCustomer() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username);
        Customer customer = customerRepository.findByUserId(user.getId()).get();
        CustomerResponseClass responseClass = new CustomerResponseClass();
        responseClass.setFirstname(customer.getUser().getFirstName());
        responseClass.setLastname(customer.getUser().getLastName());
        responseClass.setContact(customer.getContactNumber());
        responseClass.setId(customer.getUser().getId());
        responseClass.setIs_active(customer.getUser().isActive());
        return new ResponseEntity<>(responseClass, HttpStatus.CREATED);
    }

    //Api to view Customer Address(Customer-2)
    @GetMapping("/viewaddresses")
    public ResponseEntity<Set<AddressDTO>> findCustomerAddress()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        return new ResponseEntity<>(customerService.getCustomerAddress(username), HttpStatus.CREATED);
    }

    //Api to update customer profile(CUstomer-3)
    @PatchMapping("/updateprofile")
    public void updateCustomerProfile(@RequestBody CustomerResponseClass customerResponseClass
            , HttpServletRequest request, HttpServletResponse response) throws Exception{
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username);
        Customer customer = customerRepository.findByUserId(user.getId()).get();

        boolean needUpdate = false;

        if (StringUtils.hasLength(customerResponseClass.getFirstname())) {
            customer.getUser().setFirstName(customerResponseClass.getFirstname());
            needUpdate = true;
        }

        if (StringUtils.hasLength(customerResponseClass.getLastname())) {
            customer.getUser().setLastName(customerResponseClass.getLastname());
            needUpdate = true;
        }

        if (StringUtils.hasLength(customerResponseClass.getContact())) {
            customer.setContactNumber(customerResponseClass.getContact());
            needUpdate = true;
        }

        if (needUpdate) {
            customerRepository.save(customer);
        }

        JsonFormatExceptionClass.getJsonMessage("profile updated sucessfully",request,response);
    }

    //Api to Update Customer Password(Customer-4)
    @PatchMapping("/updatepassword")
    public void updateCustomerPassword(@Valid @RequestBody UpdatePassword updatePassword
            , HttpServletRequest request, HttpServletResponse response) throws Exception{
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username);
        Customer customer = customerRepository.findByUserId(user.getId()).get();
        if(updatePassword.getPassword().equals(updatePassword.getConfirmpassword())){
            user.setPassword(passwordEncoder.encode(updatePassword.getPassword()));
            user.setConfirmPassword(passwordEncoder.encode(updatePassword.getConfirmpassword()));
            customer.setUser(user);
            customerRepository.save(customer);
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


    //Api to add Customer Address(Customer-5)
    @PostMapping("/addaddress")
    public void addCustomerAddress(@RequestBody AddressResponse addressCustomerResponse
            , HttpServletRequest request, HttpServletResponse response) throws Exception{
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username);
        Customer customer = customerRepository.findByUserId(user.getId()).get();
        Set<Address> listofAddresses = customer.getUser().getAddresses();
        Address address = new Address();
        address.setCity(addressCustomerResponse.getCity());
        address.setState(addressCustomerResponse.getState());
        address.setCountry(addressCustomerResponse.getCountry());
        address.setAddressline(addressCustomerResponse.getAddressline());
        address.setZipcode(addressCustomerResponse.getZipcode());
        address.setLabel(addressCustomerResponse.getLabel());
        listofAddresses.add(address);
        customerRepository.save(customer);
        JsonFormatExceptionClass.getJsonMessage("address added successfully",request,response);
    }


    //Api to delete Customer Address(Customer-6)
    @DeleteMapping("/deleteaddress")
    public void deleteCustomerAddress(@RequestParam("id") Long id
            , HttpServletRequest request, HttpServletResponse response) throws Exception{
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username);
        Customer customer = customerRepository.findByUserId(user.getId()).get();
        Address address = addressRepository.findByUserAndAddressId(user.getId(), id);
        if(address != null){
            addressRepository.deleteById(id);
            JsonFormatExceptionClass.getJsonMessage("Address Deleted Sucessfully",request,response);
        } else {
            JsonFormatExceptionClass.getJsonMessage("Address id is invalid or doesn't belongs to you",request,response);
        }
    }

    //Api to Update Customer Address(Customer-7)
    @PatchMapping("/updateaddress")
    public void updateCustomerAddress(@RequestBody AddressResponse addressCustomerResponse
            , @RequestParam("id") Long id
            , HttpServletRequest request, HttpServletResponse response) throws Exception{
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username);
        Customer customer = customerRepository.findByUserId(user.getId()).get();
        // Long useraddressid = addressRepository.findByAddressId(customer.getUser().getId());
        Set<Address> listofAddresses = customer.getUser().getAddresses();
        boolean needUpdate = false;
        for(Address address : listofAddresses){
            if(address.getId() == id){
                if (StringUtils.hasLength(addressCustomerResponse.getCity())) {
                    address.setCity(addressCustomerResponse.getCity());
                    needUpdate = true;
                }

                if (StringUtils.hasLength(addressCustomerResponse.getState())) {
                    address.setState(addressCustomerResponse.getState());
                    needUpdate = true;
                }

                if (StringUtils.hasLength(addressCustomerResponse.getCountry())) {
                    address.setCountry(addressCustomerResponse.getCountry());
                    needUpdate = true;
                }
                if (StringUtils.hasLength(addressCustomerResponse.getAddressline())) {
                    address.setAddressline(addressCustomerResponse.getAddressline());
                    needUpdate = true;
                }

                if (StringUtils.hasLength(addressCustomerResponse.getZipcode())) {
                    address.setZipcode(addressCustomerResponse.getZipcode());
                    needUpdate = true;
                }

                if (StringUtils.hasLength(addressCustomerResponse.getLabel())) {
                    address.setLabel(addressCustomerResponse.getLabel());
                    needUpdate = true;
                }
                customerRepository.save(customer);
                JsonFormatExceptionClass.getJsonMessage("Address updated Sucessfully",request,response);
            }
        }
        JsonFormatExceptionClass.getJsonMessage("Address Id is Invalid",request,response);
    }

}
