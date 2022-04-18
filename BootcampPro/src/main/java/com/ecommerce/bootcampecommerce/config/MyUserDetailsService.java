package com.ecommerce.bootcampecommerce.config;

import com.ecommerce.bootcampecommerce.entity.Role;
import com.ecommerce.bootcampecommerce.repository.UserRepository;
import com.ecommerce.bootcampecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    private com.ecommerce.bootcampecommerce.entity.User user = new com.ecommerce.bootcampecommerce.entity.User();

    Set<GrantedAuthority> authorities = new HashSet<>();


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.ecommerce.bootcampecommerce.entity.User user = userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("user not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getAuthority().toUpperCase()));
        });
        return new User(user.getEmail(),user.getPassword()
                ,authorities);
    }


}
