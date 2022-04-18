package com.ecommerce.bootcampecommerce.dto;

import com.ecommerce.bootcampecommerce.service.JSONObjectConverter;

import javax.persistence.Convert;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;

public class ProductVariationDTO {

    @NotNull(message = "Product ID should be sspecified")
    Long productId;

    @NotNull(message = "Meta Data shound not be empty")
    @Convert(converter= JSONObjectConverter.class)
    private Map<String, Object> metadata;

    @Min(value = 1, message = "Atleast 1 quantity should be present")
    @NotNull(message = "This feild can't be left empty")
    private int quantityAvailable;

    @Min(value = 1, message = "Price should be greater than 0")
    @NotNull(message = "This feild can't be left empty")
    private float price;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
