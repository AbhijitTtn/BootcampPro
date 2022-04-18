package com.ecommerce.bootcampecommerce.entity;

import javax.persistence.*;

@Entity
@Table(name = "categoryMetaDataFieldValues")
public class CategoryMetaDataValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String values;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId" ,referencedColumnName = "id",nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryMetaDataId",referencedColumnName = "id",nullable = false)
    private CategoryMetaDataField categoryMetaDataField;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public CategoryMetaDataField getCategoryMetaDataField() {
        return categoryMetaDataField;
    }

    public void setCategoryMetaDataField(CategoryMetaDataField categoryMetaDataField) {
        this.categoryMetaDataField = categoryMetaDataField;
    }
}
