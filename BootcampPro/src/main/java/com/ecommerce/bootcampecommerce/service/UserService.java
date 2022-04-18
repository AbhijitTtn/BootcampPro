package com.ecommerce.bootcampecommerce.service;

import com.ecommerce.bootcampecommerce.customexception.UserNotActiveException;
import com.ecommerce.bootcampecommerce.dto.UserDTO;
import com.ecommerce.bootcampecommerce.entity.Role;
import com.ecommerce.bootcampecommerce.entity.User;
import com.ecommerce.bootcampecommerce.request.PasswordRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface UserService {

    User userConvertDtoToEntity(UserDTO userDTO);

    UserDTO userConvertEntityTODto(User user);

    boolean checkPasswordAndConfirmPassword(String password , String confirmPassword);

    String getUserName(String username);

    String getPassword(String username);

    Set<Role> getRoles(String username);

    void forgetPasswordSendMail(String email) throws UserNotActiveException;

    @Transactional
    String updatePassword(String token, PasswordRequest passwordRequest);
}
