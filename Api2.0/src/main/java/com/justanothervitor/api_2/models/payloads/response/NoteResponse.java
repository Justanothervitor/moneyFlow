package com.justanothervitor.api_2.models.payloads.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class NoteResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private List<String> tags;

}
