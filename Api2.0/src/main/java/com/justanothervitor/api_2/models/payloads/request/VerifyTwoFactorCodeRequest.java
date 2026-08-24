package com.justanothervitor.api_2.models.payloads.request;

import com.justanothervitor.api_2.models.Enums.VerificationType;
import lombok.Data;

@Data
public class VerifyTwoFactorCodeRequest {
    private String email;
    private String code;
}
