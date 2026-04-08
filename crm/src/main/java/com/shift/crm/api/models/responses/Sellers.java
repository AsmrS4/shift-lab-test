package com.shift.crm.api.models.responses;

import java.util.List;

public record Sellers (List<SellerResponse> sellers, Pagination pagination) {}
