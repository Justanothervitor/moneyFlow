package com.justanothervitor.api_2.models.payloads.request;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class EmailRequest {

    private String to;
    private String subject;
    private String body;
    private boolean html;
    private Map<String,Object> templateData;

}
