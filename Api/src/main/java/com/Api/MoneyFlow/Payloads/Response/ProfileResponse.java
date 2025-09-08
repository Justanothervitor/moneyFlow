package com.Api.MoneyFlow.Payloads.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponse {

    protected String username;
    protected String email;
    protected String role;

    public ProfileResponse(final String username, final String email, final String role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

}
