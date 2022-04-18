package com.ecommerce.bootcampecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotNull;

public class ProductDTO {

    private String name;
    private String brand;
    private Long categoryId;
    private String description;
    private Boolean isReturnable=false;
    private Boolean isCancellable=false;


    public ProductDTO(String name, String brand, Long categoryId,
                      String description, Boolean isReturnable, Boolean isCancellable) {
        this.name = name;
        this.brand = brand;
        this.categoryId = categoryId;
        this.description = description;
        this.isReturnable = isReturnable;
        this.isCancellable = isCancellable;
    }

    public ProductDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getReturnable() {
        return isReturnable;
    }

    public void setReturnable(Boolean returnable) {
        isReturnable = returnable;
    }

    public Boolean getCancellable() {
        return isCancellable;
    }

    public void setCancellable(Boolean cancellable) {
        isCancellable = cancellable;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}