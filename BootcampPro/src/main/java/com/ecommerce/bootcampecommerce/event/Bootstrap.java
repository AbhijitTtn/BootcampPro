package com.ecommerce.bootcampecommerce.event;

import com.ecommerce.bootcampecommerce.entity.Role;
import com.ecommerce.bootcampecommerce.entity.User;
import com.ecommerce.bootcampecommerce.repository.RoleRepository;
import com.ecommerce.bootcampecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if((Objects.isNull(roleRepo.findByAuthority("ROLE_ADMIN")))
                || (Objects.isNull(roleRepo.findByAuthority("ROLE_CUSTOMER")))
        ||(Objects.isNull(roleRepo.findByAuthority("ROLE_SELLER"))))
        {
            roleRepo.deleteAll();

            Role role1 = new Role();
            Role role2=new Role();
            Role role3=new Role();

            role1.setAuthority("ROLE_ADMIN");roleRepo.save(role1);
            role2.setAuthority("ROLE_CUSTOMER");roleRepo.save(role2);
            role3.setAuthority("ROLE_SELLER");roleRepo.save(role3);
        }

        if(Objects.isNull(userRepository.findByEmail("admin@gmail.com"))) {

            Role role=roleRepo.findByAuthority("ROLE_ADMIN");

            Set<Role> roles = new HashSet<>();
            roles.add(role);

            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setPassword(passwordEncoder.encode("Password@1"));
            user.setFirstName("Peter");
            user.setLastName("Parker");
            user.setRoles(roles);
            user.setActive(true);

            userRepository.save(user);
        }
    }
}