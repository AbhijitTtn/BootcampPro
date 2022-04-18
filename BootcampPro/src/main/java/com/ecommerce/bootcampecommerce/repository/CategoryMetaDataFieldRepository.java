package com.ecommerce.bootcampecommerce.repository;

import com.ecommerce.bootcampecommerce.entity.CategoryMetaDataField;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryMetaDataFieldRepository extends JpaRepository<CategoryMetaDataField,Long> {

    Optional<CategoryMetaDataField> findByName(String name);
}
