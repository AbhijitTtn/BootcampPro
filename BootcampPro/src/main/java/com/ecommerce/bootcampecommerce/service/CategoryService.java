package com.ecommerce.bootcampecommerce.service;

import com.ecommerce.bootcampecommerce.entity.CategoryMetaDataField;

import java.util.List;

public interface CategoryService {
    List<CategoryMetaDataField> findAllCategory();
}
