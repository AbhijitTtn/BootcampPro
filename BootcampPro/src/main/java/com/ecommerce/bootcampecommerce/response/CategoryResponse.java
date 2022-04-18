package com.ecommerce.bootcampecommerce.response;

import com.ecommerce.bootcampecommerce.entity.Category;

import java.util.List;

public class CategoryResponse {
    private List<Category> parentCategory;
    private Category childCategory;

    public List<Category> getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(List<Category> parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Category getChildCategory() {
        return childCategory;
    }

    public void setChildCategory(Category childCategory) {
        this.childCategory = childCategory;
    }
}