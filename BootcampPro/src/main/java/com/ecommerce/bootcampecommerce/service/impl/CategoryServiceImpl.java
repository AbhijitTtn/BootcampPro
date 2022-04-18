package com.ecommerce.bootcampecommerce.service.impl;

import com.ecommerce.bootcampecommerce.entity.CategoryMetaDataField;
import com.ecommerce.bootcampecommerce.repository.CategoryMetaDataFieldRepository;
import com.ecommerce.bootcampecommerce.repository.CategoryRepository;
import com.ecommerce.bootcampecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMetaDataFieldRepository categoryMetaDataFieldRepository;

    @Override
    public List<CategoryMetaDataField> findAllCategory() {
        return categoryMetaDataFieldRepository.findAll();
    }
}
