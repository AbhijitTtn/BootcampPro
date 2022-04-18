package com.ecommerce.bootcampecommerce.service;

import com.ecommerce.bootcampecommerce.dto.CategoryMetaDataFeildDTO;
import com.ecommerce.bootcampecommerce.entity.CategoryMetaDataField;

public interface CategoryMetaDataFeildService {

    CategoryMetaDataFeildDTO covertEntityToDTO(CategoryMetaDataField categoryMetaDataField);

    CategoryMetaDataField convertDTOToEntity(CategoryMetaDataFeildDTO categoryMetaDataFeildDTO);
}
