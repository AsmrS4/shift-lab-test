package com.shift.crm.api.models.responses;

import java.util.List;

public record Transactions(List<TransactionResponse> transactions, Pagination pagination) {}
