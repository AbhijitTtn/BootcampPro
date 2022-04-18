package com.ecommerce.bootcampecommerce.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashMap;
import java.util.Map;

@Converter
public class JSONObjectConverter implements AttributeConverter<Map<String, Object>, String> {
    @Override
    public String convertToDatabaseColumn(Map<String, Object> jsonData) {
        String json;
        try{
            json = new ObjectMapper().writeValueAsString(jsonData);
        } catch (Exception ex)
        {
         //extend error handling here if you want
            json = "";
        }
        return json;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String jsonDataAsJson) {
        Map<String, Object> jsonData;
        try {
            jsonData = new ObjectMapper().readValue(jsonDataAsJson, HashMap.class);
        } catch (Exception ex) {
             //extend error handling here if you want
            jsonData = null;
        }
        return jsonData;
    }
}