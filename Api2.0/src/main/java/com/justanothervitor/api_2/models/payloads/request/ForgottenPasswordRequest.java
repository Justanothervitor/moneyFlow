package com.justanothervitor.api_2.models.payloads.request;

import com.justanothervitor.api_2.models.Enums.VerificationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgottenPasswordRequest {

    private String email;
    private VerificationType type;

    public ForgottenPasswordRequest(String email, VerificationType type) {
        this.email = email;
        this.type = type;
    }
}
