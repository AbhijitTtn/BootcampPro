package com.ecommerce.bootcampecommerce.repository;

import com.ecommerce.bootcampecommerce.entity.Customer;
import com.ecommerce.bootcampecommerce.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findByUserId(Long id);
}
