package com.ecommerce.bootcampecommerce.repository;

import com.ecommerce.bootcampecommerce.entity.Product;
import com.ecommerce.bootcampecommerce.entity.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductVariationRepo extends JpaRepository<ProductVariation, Long> {

    @Query(value = "select count(pv.productId) from ProductVariation pv where pv.productId=:id", nativeQuery = true)
    public int findByProductId(@Param("id") Long id);

    @Query(value = "select * from ProductVariation pv where pv.productId=:id", nativeQuery = true)
    public List<ProductVariation> findVariationByProductId(@Param("id") Long id);

    @Query(value = "select pv.productId from ProductVariation pv where pv.id=:id" , nativeQuery = true)
    public Long findByProduct(@Param("id") Long id);
}
