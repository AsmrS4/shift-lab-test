package com.shift.crm.api.mappers;

import org.springframework.data.domain.Page;

public interface Mapper<E, R, L> {
    R mapToResponse(E entity);
    L mapToList(Page<E> entityPage);
}
