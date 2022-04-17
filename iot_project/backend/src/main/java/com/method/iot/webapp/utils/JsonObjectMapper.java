package com.method.iot.webapp.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JsonObjectMapper {

    private ObjectMapper mapper = new ObjectMapper();

    public <T> Optional<T> mapObject(String json, Class<T> type) {
        try {
            T result = mapper.readValue(json, type);
            return Optional.of(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<String> toJson(Object o) {
        ObjectWriter ow = mapper.writer();
        try {
            String json = ow.writeValueAsString(o);
            return Optional.of(json);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
