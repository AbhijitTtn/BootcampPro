package com.ecommerce.bootcampecommerce.response;

import com.ecommerce.bootcampecommerce.dto.ProductDTO;

public class SellerViewProductById {

    ProductDTO productDTO;
    String categoryName;

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}