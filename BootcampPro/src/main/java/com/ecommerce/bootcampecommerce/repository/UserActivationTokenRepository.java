package com.ecommerce.bootcampecommerce.repository;

import com.ecommerce.bootcampecommerce.entity.UserActivationToken;
import com.ecommerce.bootcampecommerce.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface UserActivationTokenRepository extends CrudRepository<UserActivationToken, Long> {

    UserActivationToken findByConfirmationToken(String confirmationToken);

    Date findByExpiryDate(Date expiryDate);

    UserActivationToken findByUser(User user);

    @Modifying
    @Query(value = "delete from UserActivationToken where confirmation_token =:token")
    void deleteConfirmationToken(@Param("token")String token);
}
