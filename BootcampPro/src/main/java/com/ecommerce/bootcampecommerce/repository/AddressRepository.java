package com.ecommerce.bootcampecommerce.repository;

import com.ecommerce.bootcampecommerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
//    @Query(value = "select user_id from address inner join user on address.id = user.id",nativeQuery = true)
//    Long findByAddressId(Long id);

    @Query(value = "select * from address where user_id=:id" , nativeQuery = true)
    @Modifying
    List<Address> findByUserId(@Param("id") Long id);


    @Query(value = "select * from address a where a.user_id=:userId && a.id=:addressId", nativeQuery = true)
    Address findByUserAndAddressId(@Param("userId") Long userId, @Param("addressId") Long addressId);
}
