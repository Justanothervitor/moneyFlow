package com.justanothervitor.api_2.models.payloads.request;

import lombok.Data;

@Data
public class VerifyCodeAndUpdatePassword {

    private String code;
    private String email;
    private String passwordForChange;

}
