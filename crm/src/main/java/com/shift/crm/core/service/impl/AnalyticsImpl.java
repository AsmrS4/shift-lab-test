package com.shift.crm.core.service.impl;

import com.shift.crm.api.models.requests.PaginationParams;
import com.shift.crm.api.models.requests.Period;
import com.shift.crm.core.persistence.entities.SellerStatistic;
import com.shift.crm.core.service.AnalyticService;
import com.shift.crm.core.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyticsImpl implements AnalyticService {
    private final StatisticService<SellerStatistic> sellerStatisticService;

    @Override
    public SellerStatistic retrieveMostProductiveSeller(Period period) {
        return sellerStatisticService.findMostProductiveByPeriod(period);
    }

    @Override
    public Page<SellerStatistic> retrieveUnProductiveSellers(Period period, Long amount, PaginationParams params) {
        return sellerStatisticService.findUnProductiveByPeriodAndAmount(period, amount, params);
    }
}
