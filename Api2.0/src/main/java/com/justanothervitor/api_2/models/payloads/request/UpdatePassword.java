package com.justanothervitor.api_2.models.payloads.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePassword {

    private String password;

    public UpdatePassword(String password) {
        this.password = password;
    }

}
