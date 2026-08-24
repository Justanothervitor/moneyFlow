package com.justanothervitor.api_2.repositories.specification;

import com.justanothervitor.api_2.models.Category;
import com.justanothervitor.api_2.models.Notes;
import com.justanothervitor.api_2.models.payloads.request.NotesFilterRequest;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public final class NotesSpecification {

    private NotesSpecification() {}

    public static Specification<Notes> withFilters(NotesFilterRequest filter) {
        return Specification.allOf(filterBySearchContent(filter.getSearchContent()),
        filterByCategory(filter.getCategoryId()),
        filterByMinPrice(filter.getMinPrice()),
        filterByMaxPrice(filter.getMaxPrice())
        );
    }

    private static Specification<Notes> filterBySearchContent(String searchContent) {
        return (root, query, cb) ->{
            if(searchContent == null || searchContent.isEmpty()) return null;
            var pattern = "%%%s%%".formatted(searchContent.toLowerCase());
            return cb.or(
                    cb.like(cb.lower(root.get("name")),pattern),
                            cb.like(cb.lower(root.get("description")),pattern)
            );
        };
    }

    private static Specification<Notes> filterByCategory(Long categoryId) {
        return (root, query,cb) ->
            categoryId == null ? null : cb.equal(root.get("categoryId").get("id"),categoryId);
    }

    private static Specification<Notes> filterByMinPrice(BigDecimal minPrice) {
        return (root, query, cb) ->
                minPrice == null ? null : cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    private static Specification<Notes> filterByMaxPrice(BigDecimal maxPrice) {
        return (root, query, cb) ->
                maxPrice == null ? null : cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }



}
