package com.ecommerce.bootcampecommerce.repository;

import com.ecommerce.bootcampecommerce.entity.Address;
import com.ecommerce.bootcampecommerce.entity.Customer;
import com.ecommerce.bootcampecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUserId(Long id);

    @Query(value = "select * from address a where a.user_id=:userId && a.id=:addressId", nativeQuery = true)
    Address findByUserAndAddressId(@Param("userId") Long userId, @Param("addressId") Long addressId);

}
