package com.ecommerce.bootcampecommerce.response;

import com.ecommerce.bootcampecommerce.entity.Product;
import com.ecommerce.bootcampecommerce.entity.ProductVariation;

public class ViewProductVariationResponse {

    Product product;
    ProductVariation productVariation;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }
}
