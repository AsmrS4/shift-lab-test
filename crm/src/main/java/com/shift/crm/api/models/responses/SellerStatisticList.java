package com.shift.crm.api.models.responses;

import java.util.List;

public record SellerStatisticList(List<SellerStatisticResponse> statistic, Pagination pagination){}
