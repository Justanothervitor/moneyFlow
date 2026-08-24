package com.justanothervitor.api_2.models.payloads.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class VerificationResponse {

    private boolean success;
    private String message;
    private LocalDateTime expiryDate;

}
