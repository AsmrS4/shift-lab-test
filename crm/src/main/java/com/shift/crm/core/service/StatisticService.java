package com.shift.crm.core.service;

import com.shift.crm.api.models.requests.PaginationParams;
import com.shift.crm.api.models.requests.Period;
import org.springframework.data.domain.Page;

public interface StatisticService<T>{
    T findMostProductiveByPeriod(Period period);
    Page<T> findUnProductiveByPeriodAndAmount(Period period, Long amount, PaginationParams params);
}
