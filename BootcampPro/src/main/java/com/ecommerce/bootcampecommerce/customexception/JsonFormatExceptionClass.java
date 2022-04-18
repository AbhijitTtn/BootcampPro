package com.ecommerce.bootcampecommerce.customexception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class JsonFormatExceptionClass {
    public static void getJsonMessage(String message, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> customMessage = new HashMap<>();
        String msg = message;
        customMessage.put("message",msg);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(),customMessage);
    }
}
