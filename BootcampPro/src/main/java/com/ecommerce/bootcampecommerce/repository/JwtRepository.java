package com.ecommerce.bootcampecommerce.repository;

import com.ecommerce.bootcampecommerce.utils.JwtBlacklist;
import org.springframework.data.repository.CrudRepository;

public interface JwtRepository extends CrudRepository<JwtBlacklist,String> {

    JwtBlacklist findByToken(String token);
}
