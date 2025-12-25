package com.justanothervitor.api_2.models.payloads.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdatePayload {

    private String username;
    private String email;


    public UserUpdatePayload(String username, String email) {
        this.username = username;
        this.email = email;
    }



}
