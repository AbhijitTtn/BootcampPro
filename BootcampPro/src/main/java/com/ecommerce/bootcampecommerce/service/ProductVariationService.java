package com.ecommerce.bootcampecommerce.service;

import com.ecommerce.bootcampecommerce.dto.ProductVariationDTO;
import com.ecommerce.bootcampecommerce.entity.ProductVariation;

public interface ProductVariationService {

    public ProductVariationDTO entityToDTO(ProductVariation productVariation);

    public ProductVariation DTOToEntity(ProductVariationDTO productVariationDTO);
}
