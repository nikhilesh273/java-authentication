package com.jwt_authentication.jwt_authentication.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.List;

/**
 * @author nikhilesh babu mt
 * @created 29-Mar-2022
 */
public class MapperUtil {

    private static final ModelMapper modelMapper = new ModelMapper();
    private static final ObjectMapper jsonObjectMapper = new ObjectMapper();

    public static <T, D> T objectToClass(D input, Class<T> outClass) {
        return modelMapper.map(input, outClass);
    }

    public static <T> T strToJson(String input, Class<T> outClass)
            throws JsonParseException, JsonMappingException, IOException {
        return jsonObjectMapper.readValue(input, outClass);
    }

    public static <T> List<T> strToJsonList(String input, TypeReference<List<T>> outClass)
            throws JsonParseException, JsonMappingException, IOException {
        return jsonObjectMapper.readValue(input, outClass);
    }

    public static <T, D> T objectToObject(D input, Class<T> outClass)
            throws JsonParseException, JsonMappingException, IOException {
        return jsonObjectMapper.convertValue(input, outClass);
    }

    public static <D> String jsonToStr(D input) throws JsonParseException, JsonMappingException, IOException {
        return jsonObjectMapper.writeValueAsString(input);
    }

}

