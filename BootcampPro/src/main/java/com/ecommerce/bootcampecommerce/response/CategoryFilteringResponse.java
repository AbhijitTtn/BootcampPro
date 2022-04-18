package com.ecommerce.bootcampecommerce.response;

import com.ecommerce.bootcampecommerce.entity.CategoryMetaDataValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryFilteringResponse {

    Map<String, String> categoryMetaDataValues=new HashMap<>();
    List<String> brands=new ArrayList<>();
    float maxPrice=0;
    float minPrice=0;

    public Map<String, String> getCategoryMetaDataValues() {
        return categoryMetaDataValues;
    }

    public void setCategoryMetaDataValues(Map<String, String> categoryMetaDataValues) {
        this.categoryMetaDataValues = categoryMetaDataValues;
    }

    public List<String> getBrands() {
        return brands;
    }

    public void setBrands(List<String> brands) {
        this.brands = brands;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }
}
