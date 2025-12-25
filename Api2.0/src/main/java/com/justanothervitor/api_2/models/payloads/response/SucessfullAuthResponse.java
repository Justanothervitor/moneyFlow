package com.justanothervitor.api_2.models.payloads.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SucessfullAuthResponse {

    private String token;
    private String username;
    private String email;

    public SucessfullAuthResponse (final String token, final String username, final String email)
    {
        this.token = token;
        this.username = username;
        this.email = email;
    }

}
