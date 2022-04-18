package com.ecommerce.bootcampecommerce.service.impl;

import com.ecommerce.bootcampecommerce.dto.CategoryMetaDataFeildDTO;
import com.ecommerce.bootcampecommerce.entity.CategoryMetaDataField;
import com.ecommerce.bootcampecommerce.service.CategoryMetaDataFeildService;
import org.hibernate.validator.constraints.CodePointLength;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CategoryMetaDataFeildServiceImpl implements CategoryMetaDataFeildService {
    @Override
    public CategoryMetaDataFeildDTO covertEntityToDTO(CategoryMetaDataField categoryMetaDataField) {
        CategoryMetaDataFeildDTO categoryMetaDataFeildDTO = new CategoryMetaDataFeildDTO();
        categoryMetaDataFeildDTO.setName(categoryMetaDataField.getName());
        return categoryMetaDataFeildDTO;
    }

    @Override
    public CategoryMetaDataField convertDTOToEntity(CategoryMetaDataFeildDTO categoryMetaDataFeildDTO) {
        CategoryMetaDataField categoryMetaDataField = new CategoryMetaDataField();
        categoryMetaDataField.setName(categoryMetaDataFeildDTO.getName());
        return categoryMetaDataField;
    }
}
