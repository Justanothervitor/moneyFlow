package com.justanothervitor.api_2.models.payloads.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordRequest {

    private String password;

    public UpdatePasswordRequest(String password)
    {
        this.password = password;
    }

}
