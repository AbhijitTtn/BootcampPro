package com.ecommerce.bootcampecommerce.repository;

import com.ecommerce.bootcampecommerce.entity.CategoryMetaDataValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryMetaDataValueRepository extends JpaRepository<CategoryMetaDataValue,Long> {

    @Query(value = "select * from categoryMetaDataFieldValues where categoryMetaDataId=:meta_data_id && categoryId=:cid",nativeQuery = true)
    CategoryMetaDataValue findByMetaId(@Param("meta_data_id") Long id, @Param("cid") Long cid);

    List<CategoryMetaDataValue> findByCategoryId(Long id);
}
