package com.ecommerce.bootcampecommerce.service.impl;

import com.ecommerce.bootcampecommerce.customexception.UserAlreadyEsixt;
import com.ecommerce.bootcampecommerce.dto.SellerDTO;
import com.ecommerce.bootcampecommerce.entity.Address;
import com.ecommerce.bootcampecommerce.entity.Role;
import com.ecommerce.bootcampecommerce.entity.Seller;
import com.ecommerce.bootcampecommerce.entity.User;
import com.ecommerce.bootcampecommerce.repository.RoleRepository;
import com.ecommerce.bootcampecommerce.repository.SellerRepository;
import com.ecommerce.bootcampecommerce.repository.UserRepository;
import com.ecommerce.bootcampecommerce.service.AddressService;
import com.ecommerce.bootcampecommerce.service.SellerService;
import com.ecommerce.bootcampecommerce.service.UserService;
import com.ecommerce.bootcampecommerce.token.EmailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SellerServiceImpl implements SellerService {
        @Autowired
        SellerRepository SellerRepository;

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
        public List<SellerDTO> getSellers() {
            return SellerRepository.findAll().stream()
                    .map(o -> sellerConvertEntityToDto(o)).collect(Collectors.toList());
        }

        @Override
        public SellerDTO sellerConvertEntityToDto(Seller seller) {
            return new SellerDTO(
                    seller.getGST(),
                    seller.getCompanyContact(),
                    seller.getCompanyName(),
                    userService.userConvertEntityTODto(seller.getUser()),
                    addressService.addressConvertEntityTODto(seller.getUser().getAddresses()));
        }

        @Override
        public Seller sellerConvertDtoToEntity(SellerDTO sellerDTO) {
            Seller seller = new Seller();
            seller.setCompanyContact(sellerDTO.getCompanyContact());
            seller.setCompanyName(sellerDTO.getCompanyName());
            seller.setGST(sellerDTO.getGST());
            return seller;
        }

        @Override
        public SellerDTO saveSeller(SellerDTO sellerDTO) {

            User user = userService.userConvertDtoToEntity(sellerDTO.getUserDTO());

            User userExist = userRepository.findByEmail(user.getEmail());
            if (userExist != null)
                throw new UserAlreadyEsixt("USER ALREADY EXIST WITH EMAIL" + userExist.getEmail() + "\n TRY AGAIN");

            Set<Address> addresses=sellerDTO.getAddressDTO().
                    stream().map(s->addressService.addressConvertDtoToEntity(s)).collect(Collectors.toSet());

            user.setAddresses(addresses);

            Seller Seller = sellerConvertDtoToEntity(sellerDTO);

            Role role = roleRepository.findByAuthority("ROLE_SELLER");
            Set<Role> roleList = new HashSet<>();
            roleList.add(role);
            user.setRoles(roleList);

            if (userService.checkPasswordAndConfirmPassword(user.getPassword(),user.getConfirmPassword())) {

                user.setPassword(passwordEncoder.encode(user.getPassword()));
                Seller.setUser(user);

                SellerRepository.save(Seller);

                sendEmail(user);
                return sellerDTO;

            }
            throw new RuntimeException("error occured while creating user");
        }
        

        @Override
        public void sendEmail(User user) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setFrom("test@gmail.com");
            message.setSubject("Registration Complete");
            message.setText("YOUR ACCOUNT HAS BEEN CREATED \n WAIT FOR APPROVAL");
            emailSendService.sendEmail(message);
        }

    }
