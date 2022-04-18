package com.ecommerce.bootcampecommerce.dto;

public class CategoryMetaDataFeildDTO {

    private String name;

    public CategoryMetaDataFeildDTO(){}

    CategoryMetaDataFeildDTO(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
