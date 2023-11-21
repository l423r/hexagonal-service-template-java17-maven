package ru.nota.hexagonal.template.common.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.Sort;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

@Value
@Builder
public class PageSearchRequest {

    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final Sort.Direction DEFAULT_SORT_TYPE = Sort.Direction.ASC;
    public static final String DEFAULT_SORT_FIELD = "id";
    Integer pageSize;
    Integer page;
    Sort.Direction sortDirection;
    String sortField;

    public boolean isEmpty() {
        return page == null || pageSize == null;
    }

    public boolean hasSort() {
        return nonNull(sortField);
    }

    public Integer getPageSize() {
        return ofNullable(pageSize)
            .orElse(DEFAULT_PAGE_SIZE);
    }

    public Integer getPage() {
        return ofNullable(page)
            .orElse(DEFAULT_PAGE_NUMBER);
    }

    public Sort.Direction getSortDirection() {
        return ofNullable(sortDirection)
            .orElse(DEFAULT_SORT_TYPE);
    }

    public String getSortField() {
        return ofNullable(sortField)
            .orElse(DEFAULT_SORT_FIELD);
    }
}
