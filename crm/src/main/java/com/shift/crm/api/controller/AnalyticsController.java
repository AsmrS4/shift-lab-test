package com.shift.crm.api.controller;

import com.shift.crm.api.mappers.Mapper;
import com.shift.crm.api.models.requests.PaginationParams;
import com.shift.crm.api.models.requests.Period;
import com.shift.crm.api.models.responses.SellerStatisticList;
import com.shift.crm.api.models.responses.SellerStatisticResponse;
import com.shift.crm.core.persistence.entities.SellerStatistic;
import com.shift.crm.core.service.AnalyticService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytic/sellers")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticService service;
    private final Mapper<SellerStatistic, SellerStatisticResponse, SellerStatisticList> mapper;

    @GetMapping("/productive")
    public SellerStatisticResponse retrieveMostProductiveSeller(@ParameterObject @Valid Period period) {
        SellerStatistic statistic = service.retrieveMostProductiveSeller(period);
        return mapper.mapToResponse(statistic);
    }

    @GetMapping("/non-productive")
    public SellerStatisticList retrieveUnProductiveSellers(@ParameterObject @Valid Period period, @RequestParam("amount") Long amount, @ParameterObject @Valid PaginationParams params) {
        Page<SellerStatistic> sellers = service.retrieveUnProductiveSellers(period, amount, params);
        return mapper.mapToList(sellers);
    }
}
