package com.justanothervitor.api_2.models.payloads.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NotesFilterRequest {
    private String searchContent;
    private Long categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    private int page = 0;
    private int size = 10;

    private String orderFor = "name";
    private String direction = "asc";
}
