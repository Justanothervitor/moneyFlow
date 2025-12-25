package com.justanothervitor.api_2.models;

import lombok.Data;

import java.util.Map;

@Data
public class Oauth2InfoClass {

    private Map<String,Object> attributes;
    private String id;
    private String username;
    private String email;

    public Oauth2InfoClass(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
