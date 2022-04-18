package com.ecommerce.bootcampecommerce.service.impl;

import com.ecommerce.bootcampecommerce.dto.ProductVariationDTO;
import com.ecommerce.bootcampecommerce.entity.ProductVariation;
import com.ecommerce.bootcampecommerce.service.ProductVariationService;
import org.springframework.stereotype.Service;

@Service
public class ProductVariationImpl implements ProductVariationService {

    @Override
    public ProductVariationDTO entityToDTO(ProductVariation productVariation) {
        ProductVariationDTO productVariationDTO = new ProductVariationDTO();
        productVariationDTO.setMetadata(productVariation.getMetadata());
        productVariationDTO.setPrice(productVariation.getPrice());
        productVariationDTO.setQuantityAvailable(productVariation.getQuantityAvailable());

        return productVariationDTO;
    }

    @Override
    public ProductVariation DTOToEntity(ProductVariationDTO productVariationDTO) {
        ProductVariation productVariation = new ProductVariation();
        productVariation.setMetadata(productVariationDTO.getMetadata());
        productVariation.setQuantityAvailable(productVariationDTO.getQuantityAvailable());
        productVariation.setPrice(productVariationDTO.getPrice());

        return  productVariation;
    }
}
