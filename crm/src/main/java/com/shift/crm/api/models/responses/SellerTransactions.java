package com.shift.crm.api.models.responses;

import java.util.List;

public record SellerTransactions(SellerResponse seller, List<TransactionShort> transactions, Pagination pagination){
}
