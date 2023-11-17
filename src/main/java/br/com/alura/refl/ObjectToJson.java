package br.com.alura.refl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ObjectToJson {

    public String transform(Object object) {
        String result = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            Map<String, Object> mapper = new HashMap<>();
            Class<?> classToBeTransformed = object.getClass();

            Arrays.stream(classToBeTransformed.getDeclaredFields()).toList().forEach(
                    field -> {
                        field.setAccessible(true);
                        String fieldName = field.getName();
                        Object fieldValue = null;
                        try {
                            fieldValue = field.get(object);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        mapper.put(fieldName, fieldValue);
                    }
            );
            result = objectMapper.writeValueAsString(mapper);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
