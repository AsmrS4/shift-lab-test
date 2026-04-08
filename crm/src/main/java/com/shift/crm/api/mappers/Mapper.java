package com.shift.crm.api.mappers;

import com.shift.crm.api.models.responses.Pagination;
import org.springframework.data.domain.Page;

import java.util.List;

public abstract class Mapper<E, R, L> {
    public abstract R mapToResponse(E entity);
    public L mapToList(Page<E> entityPage) {
        List<E> entities = entityPage.getContent();
        List<R> records = entities.stream().map(this::mapToResponse).toList();

        Pagination pagination = getPagination(entityPage);

        return createResponse(records, pagination);
    }

    protected abstract L createResponse(List<R> records, Pagination pagination);

    protected Pagination getPagination(Page<E> entityPage) {
        int currentPage = entityPage.getNumber();
        int pages = entityPage.getTotalPages();
        int size = (int) entityPage.getTotalElements();

        return new Pagination(size, pages, currentPage);
    }
}
