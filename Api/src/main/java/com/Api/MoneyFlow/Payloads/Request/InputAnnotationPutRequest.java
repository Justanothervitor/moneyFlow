package com.Api.MoneyFlow.Payloads.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputAnnotationPutRequest {

    protected String name;
    protected Double value;
    protected String description;

    public InputAnnotationPutRequest(String name, Double value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
    }
}
