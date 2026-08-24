package com.justanothervitor.api_2.models.payloads.request;

import com.justanothervitor.api_2.models.Enums.VerificationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendVerificationCodeRequest {

    @Email
    @NotBlank
    private String email;

    @NotNull
    private VerificationType type;
}
