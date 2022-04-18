package com.ecommerce.bootcampecommerce.request;

import com.ecommerce.bootcampecommerce.service.JSONObjectConverter;

import javax.persistence.Convert;
import javax.validation.constraints.NotNull;
import java.util.Map;

public class UpdateProductVariationRequest {

    @NotNull(message = "Id should be passed to update")
    public Long productVariationId;

    private Integer quantityAvailable;

    @Convert(converter= JSONObjectConverter.class)
    private Map<String, Object> metadata;

    private Float price;

    public Boolean isActive;

    public Long getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(Long productVariationId) {
        this.productVariationId = productVariationId;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
