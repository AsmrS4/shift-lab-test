package com.shift.crm.core.service;

import com.shift.crm.api.models.requests.PaginationParams;
import com.shift.crm.api.models.requests.Period;
import com.shift.crm.core.persistence.entities.SellerStatistic;
import org.springframework.data.domain.Page;

public interface AnalyticService {
    SellerStatistic retrieveMostProductiveSeller(Period period);
    Page<SellerStatistic> retrieveUnProductiveSellers(Period period, Long amount, PaginationParams params);
}
