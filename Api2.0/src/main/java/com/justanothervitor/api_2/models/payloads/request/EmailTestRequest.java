package com.justanothervitor.api_2.models.payloads.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailTestRequest {

    private String email;

    public EmailTestRequest(String email) {
        this.email = email;
    }
}
