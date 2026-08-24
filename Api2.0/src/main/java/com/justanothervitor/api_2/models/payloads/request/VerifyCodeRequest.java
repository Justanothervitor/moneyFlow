package com.justanothervitor.api_2.models.payloads.request;

import com.justanothervitor.api_2.models.Enums.VerificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VerifyCodeRequest {

    @NotBlank
    private String code;

    @NotNull
    private VerificationType type;
}
