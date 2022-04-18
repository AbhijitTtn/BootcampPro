package com.ecommerce.bootcampecommerce.service.impl;

import com.ecommerce.bootcampecommerce.customexception.UserAlreadyEsixt;
import com.ecommerce.bootcampecommerce.dto.AddressDTO;
import com.ecommerce.bootcampecommerce.dto.CustomerDTO;
import com.ecommerce.bootcampecommerce.entity.Customer;
import com.ecommerce.bootcampecommerce.entity.Role;
import com.ecommerce.bootcampecommerce.entity.User;
import com.ecommerce.bootcampecommerce.repository.CustomerRepository;
import com.ecommerce.bootcampecommerce.repository.RoleRepository;
import com.ecommerce.bootcampecommerce.repository.UserRepository;
import com.ecommerce.bootcampecommerce.service.AddressService;
import com.ecommerce.bootcampecommerce.service.CustomerService;
import com.ecommerce.bootcampecommerce.entity.UserActivationToken;
import com.ecommerce.bootcampecommerce.service.UserService;
import com.ecommerce.bootcampecommerce.repository.UserActivationTokenRepository;
import com.ecommerce.bootcampecommerce.token.EmailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {


    @Autowired
    private UserActivationTokenRepository confirmationTokenRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private EmailSendService emailSendService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;


    @Override
    public List<CustomerDTO> getCustomers() {
        return customerRepository.findAll().stream()
                .map(o ->customerConvertEntityToDto(o)).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO customerConvertEntityToDto(Customer customer)
    {
        return new CustomerDTO(customer.getContactNumber(),
                userService.userConvertEntityTODto(customer.getUser()));
    }

    @Override
    public Customer customerConvertDtoToEntity(CustomerDTO customerDTO)
    {
        Customer customer = new Customer();
        customer.setContactNumber(customerDTO.getContactNumber());
        return customer;
    }


    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO)
    {
        User user=userService.userConvertDtoToEntity(customerDTO.getUserDTO());
        User userExist=userRepository.findByEmail(user.getEmail());
        if(userExist!=null)
            throw new UserAlreadyEsixt("USER ALREADY EXIST WITH EMAIL"+" "+userExist.getEmail()+"\n TRY AGAIN");

        Customer customer=customerConvertDtoToEntity(customerDTO);

        Role role = roleRepository.findByAuthority("ROLE_CUSTOMER");
        Set<Role> roleList = new HashSet<>();
        roleList.add(role);
        user.setRoles(roleList);

        if (userService.checkPasswordAndConfirmPassword(user.getPassword(),user.getConfirmPassword())) {

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            customer.setUser(user);

            customerRepository.save(customer);

            sendEmail(user);
            return customerDTO;
        }
            throw new RuntimeException("error occurred while creating user");
    }



    @Transactional
    @Override
    public String resendToken(User user)
    {
        if (user.isActive() == false) {
            UserActivationToken confirmationToken = confirmationTokenRepository.findByUser(user);
            confirmationTokenRepository.deleteConfirmationToken(confirmationToken.getConfirmationToken());
            sendEmail(user);
            return "new email sent";
        } else
            return "Account already active";
    }

    @Transactional
    @Override
    public String checkEmail(String email) {

        System.out.println(email);
        User user=userRepository.findByEmail(email);
        if (user!=null)
            return resendToken(user);
        else
            return "email doesnot exist";

    }

    @Override
    public void sendEmail( User user){
        UserActivationToken confirmationToken= new UserActivationToken(user);
        confirmationTokenRepository.save(confirmationToken);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setFrom("test@gmail.com");
        message.setSubject("Complete Registration");
        message.setText("To confirm your account, please click here:"+"http://localhost:9092/register/customer/confirm?token="
                +confirmationToken.getConfirmationToken());
        emailSendService.sendEmail(message);
    }

    @Transactional
    @Override
    public String confirmToken(String token) {

        UserActivationToken confirmationToken=confirmationTokenRepository.findByConfirmationToken(token);
        if(confirmationToken!=null)
        {

            Date expiryDate=confirmationToken.getExpiryDate();
            if(expiryDate.before(new Date()))
            {
                confirmationTokenRepository.deleteConfirmationToken(token);
                sendEmail(confirmationToken.getUser());
                String tokenExpired="TOKEN EXPIRED+\n mail has been sent to your email id with another token link";

                return tokenExpired;
            }
            else
            {
                User user = confirmationToken.getUser();
                System.out.println(user.getId());
                User user1 = userRepository.getById(user.getId());
                user.setActive(true);
                userRepository.save(user1);
                confirmationTokenRepository.deleteConfirmationToken(token);
                return "confirm";
            }
        }
        else {
            String tokenInvalid="TOKEN INVALID \n CLICK ON THIS LINK:http://localhost:9092/register/customer/resendToken";
            return tokenInvalid;

        }
    }

    @Override
    public Set<AddressDTO> getCustomerAddress(String email) {
        User user=userRepository.findByEmail(email)
                ;
        return addressService.addressConvertEntityTODto(user.getAddresses());
    }




}
