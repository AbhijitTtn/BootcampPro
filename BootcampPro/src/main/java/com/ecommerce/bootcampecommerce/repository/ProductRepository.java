package com.ecommerce.bootcampecommerce.repository;

import com.ecommerce.bootcampecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select * from product where categoryId=:id",nativeQuery = true)
    List<Product> findByCategoryId(Long id);


    @Query(value = "select * from product where sellerUserId=:sId and id=:pId",nativeQuery = true)
    Product findBySellerAndProductId(@Param("sId") Long sellerId, @Param("pId") Long productId);

    @Query(value = " select min(pv.price) , max(pv.price) from product p inner join ProductVariation" +
            " pv on p.id=pv.productId where p.categoryId=:id" , nativeQuery = true)
    List<Object[]> findByJoinProductVariation(@Param("id") Long id);

    @Query(value = "select * from product p where p.name=:name and p.brand=:brand and p.categoryId=:cId and p.sellerUserId=:uId",
            nativeQuery = true)
    Product findProductName(@Param("name") String productName, @Param("brand") String brand,
                            @Param("cId") Long categoryId, @Param("uId") Long userId);

    @Query(value = "select * from product where sellerUserId=:sId and isDeleted=false",nativeQuery = true)
    List<Product> findBySellerId(@Param("sId") Long sellerId);
}
