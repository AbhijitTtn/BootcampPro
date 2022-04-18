package com.ecommerce.bootcampecommerce.repository;

import com.ecommerce.bootcampecommerce.entity.Category;
import com.ecommerce.bootcampecommerce.entity.CategoryMetaDataField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select * from Category where parentCategoryId=:id",nativeQuery = true)
    public List<Category> findByParentCategoryId(@Param("id") Long id);

    public Category findByCategoryName(String categoryname);

    @Query("from Category where parentCategoryId is null")
    public List<Category> findAllRootCategory();

}
