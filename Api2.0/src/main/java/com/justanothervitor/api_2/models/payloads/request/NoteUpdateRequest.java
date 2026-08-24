package com.justanothervitor.api_2.models.payloads.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class NoteUpdateRequest {

    private String name;
    private String description;
    private BigDecimal price;
    private List<String> tags;

}
