package com.justanothervitor.api_2.models.payloads.response;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public record NotePagesResponse<T>(
        List<T> content,int actualPage, int totalOfPages, long totalElements,
        int sizeOfPage, boolean first, boolean last,
        boolean isNull, Map<String, Object> appliedFilters){

    public static <T> NotePagesResponse<T> from(Page<T> page, Map<String, Object> appliedFilters)
    {
        return new NotePagesResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize(),
                page.isFirst(),
                page.isLast(),
                page.isEmpty(),
                appliedFilters
        );
    }

}
