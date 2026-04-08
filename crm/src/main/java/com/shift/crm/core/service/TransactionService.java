package com.shift.crm.core.service;

import com.shift.crm.api.models.requests.CreateTransaction;
import com.shift.crm.api.models.requests.PaginationParams;
import com.shift.crm.core.persistence.enities.Transaction;
import org.springframework.data.domain.Page;

public interface TransactionService {
    Transaction createTransaction(Long sellerId, CreateTransaction createTransaction);
    Transaction retrieveTransactionDetails(Long transactionId);
    Page<Transaction> retrieveTransactions(PaginationParams params);
    Page<Transaction> retrieveSellerTransactions(Long sellerId, PaginationParams params);
}
