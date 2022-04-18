package com.ecommerce.bootcampecommerce.repository;
import com.ecommerce.bootcampecommerce.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByAuthority(String name);
}
