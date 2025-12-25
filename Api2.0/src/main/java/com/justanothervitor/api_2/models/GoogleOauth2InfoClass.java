package com.justanothervitor.api_2.models;

import java.util.Map;

public class GoogleOauth2InfoClass  extends Oauth2InfoClass{

    public GoogleOauth2InfoClass(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) getAttributes().get("sub");
    }

    @Override
    public String getUsername() {
        return (String) getAttributes().get("name");
    }

    @Override
    public String getEmail() {
        return (String) getAttributes().get("email");
    }
}
