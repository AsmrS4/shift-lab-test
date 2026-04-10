package com.shift.crm.core.utils;

import com.shift.crm.api.models.requests.PaginationParams;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageableConverter {
    public static Pageable toPageable(PaginationParams params) {
        return PageRequest.of(params.getPage(), params.getSize());
    }

    public static Pageable toPageableWithSortDesc(PaginationParams params, String orderBy) {
        return PageRequest.of(params.getPage(), params.getSize(), Sort.by(orderBy).descending());
    }

    public static Pageable toPageableWithSortAsc(PaginationParams params, String orderBy) {
        return PageRequest.of(params.getPage(), params.getSize(), Sort.by(orderBy).ascending());
    }
}
