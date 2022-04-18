package com.ecommerce.bootcampecommerce.service.impl;

import com.ecommerce.bootcampecommerce.customexception.PasswordAndConfirmPasswordMismatchException;
import com.ecommerce.bootcampecommerce.customexception.UserNotActiveException;
import com.ecommerce.bootcampecommerce.dto.UserDTO;
import com.ecommerce.bootcampecommerce.entity.ForgetPasswordToken;
import com.ecommerce.bootcampecommerce.entity.Role;
import com.ecommerce.bootcampecommerce.entity.User;
import com.ecommerce.bootcampecommerce.repository.ForgetPasswordTokenRepository;
import com.ecommerce.bootcampecommerce.repository.UserRepository;
import com.ecommerce.bootcampecommerce.request.PasswordRequest;
import com.ecommerce.bootcampecommerce.service.UserService;
import com.ecommerce.bootcampecommerce.token.EmailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ForgetPasswordTokenRepository forgetPasswordTokenRepository;

    @Autowired
    EmailSendService emailSendService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User userConvertDtoToEntity(UserDTO userDTO)
    {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setPassword(userDTO.getPassword());
        user.setMiddleName(userDTO.getMiddleName());
        user.setConfirmPassword(userDTO.getConfirmPassword());
        user.setPasswordUpdateDate(new Date());
        user.setLastName(userDTO.getLastName());
        return user;
    }

    @Override
    public UserDTO userConvertEntityTODto(User user) {
        return new UserDTO(user.getId(), user.getEmail(),user.getFirstName(),user.getMiddleName(),user.getLastName(),
                user.getRoles(), user.getPasswordUpdateDate());
    }

    @Override
    public boolean checkPasswordAndConfirmPassword(String password , String confirmPassword) {
        if(confirmPassword.equals(password))
            return true;
        else
            throw  new PasswordAndConfirmPasswordMismatchException("Password and confirm password donot match," +
                    "\n check and try again");
    }

    @Override
    public String getUserName( String username){
        User user = userRepository.findByEmail(username);
        String username1 = user.getEmail();
        return username1;
    }

    @Override
    public String getPassword(String username){
        User user = userRepository.findByEmail(username);
        String password = user.getPassword();
        return password;
    }

    @Override
    public Set<Role> getRoles(String username)
    {
        User user=userRepository.findByEmail(username);
        Set<Role> roles=user.getRoles();
        return roles;
    }

    @Override
    public void forgetPasswordSendMail(String email) throws UserNotActiveException {

        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (user.isActive()) {
                ForgetPasswordToken forgetPasswordToken = new ForgetPasswordToken(user);
                forgetPasswordTokenRepository.save(forgetPasswordToken);
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(user.getEmail());
                message.setFrom("test@gmail.com");
                message.setSubject("PASSWORD UPDATION");
                message.setText("To change your password, please click here:" + "http://localhost:9092/forgetPassword/updatePassword?token="
                        + forgetPasswordToken.getConfirmationToken());
                emailSendService.sendEmail(message);
            } else
                throw new UserNotActiveException("User is not active");
        }
        else
            throw new EntityNotFoundException("entity not found");
    }

    @Transactional
    @Override
    public String updatePassword(String token, PasswordRequest passwordRequest) {
        ForgetPasswordToken forgetPasswordToken = forgetPasswordTokenRepository.findByConfirmationToken(token);
        if (forgetPasswordToken != null) {
            Date expiryDate = forgetPasswordToken.getExpiryDate();
            if (expiryDate.before(new Date())) {
                forgetPasswordTokenRepository.deleteConfirmationToken(token);
                User user = forgetPasswordToken.getUser();
                User user1 = userRepository.getById(user.getId());
                forgetPasswordSendMail(user1.getEmail());
                String tokenExpired = "TOKEN EXPIRED+\n mail has been sent to your email id with another token link";
                return tokenExpired;
            }
            else {
                User user = forgetPasswordToken.getUser();
                System.out.println(user.getId());
                User user1 = userRepository.getById(user.getId());
                checkPasswordAndConfirmPassword(passwordRequest.getPassword(),passwordRequest.getConfirmPassword());
                //user1.setPassword(passwordRequest.getPassword());
                user1.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
                user1.setPasswordUpdateDate(new Date());
                userRepository.save(user1);
                forgetPasswordTokenRepository.deleteConfirmationToken(token);
                return "password changed";
            }
        }

        else {
            String tokenInvalid="TOKEN INVALID";
            return tokenInvalid;

        }
    }
}
