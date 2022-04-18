package com.ecommerce.bootcampecommerce.service;

import com.ecommerce.bootcampecommerce.dto.AddressDTO;
import com.ecommerce.bootcampecommerce.dto.CustomerDTO;
import com.ecommerce.bootcampecommerce.dto.UserDTO;
import com.ecommerce.bootcampecommerce.entity.Customer;
import com.ecommerce.bootcampecommerce.entity.User;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;


public interface CustomerService {

    public List<CustomerDTO> getCustomers();

    CustomerDTO customerConvertEntityToDto(Customer customer);

    Customer customerConvertDtoToEntity(CustomerDTO customerDTO);

    public CustomerDTO saveCustomer(CustomerDTO customerDTO);


    @Transactional
    String resendToken(User user);

    String checkEmail(String email);

    void sendEmail(User user);

    public String confirmToken(String token);

    public  Set<AddressDTO> getCustomerAddress(String email);
}
