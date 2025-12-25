package com.justanothervitor.api_2.models.payloads.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginPayload {

    private String username;
    private String password;

    public LoginPayload(final String username, final String password)
    {
        this.username = username;
        this.password = password;
    }
}
