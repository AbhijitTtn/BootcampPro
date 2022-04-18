package com.ecommerce.bootcampecommerce.repository;

import com.ecommerce.bootcampecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);


}
