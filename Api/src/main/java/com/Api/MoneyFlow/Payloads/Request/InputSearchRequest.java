package com.Api.MoneyFlow.Payloads.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputSearchRequest {

    private String search;

    public InputSearchRequest(String search) {
        this.search = search;
    }
}
