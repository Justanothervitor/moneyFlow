package com.justanothervitor.api_2.models.payloads.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserPayload {

    private String username;
    private String email;
    private String password;


    public CreateUserPayload(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
