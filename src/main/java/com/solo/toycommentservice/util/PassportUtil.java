package com.solo.toycommentservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PassportUtil {

    private Map<String, String> passport;

    public String getUsername(String passportJson) throws JsonProcessingException {

        convertPassport(passportJson);
        return passport.get("username");
    }

    public void convertPassport(String passportJson) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        passport = mapper.readValue(passportJson, Map.class);
    }
}
