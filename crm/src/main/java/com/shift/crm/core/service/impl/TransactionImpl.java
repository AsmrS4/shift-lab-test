package com.shift.crm.core.service.impl;

import com.shift.crm.api.models.requests.CreateTransaction;
import com.shift.crm.api.models.requests.PaginationParams;
import com.shift.crm.core.persistence.enities.Seller;
import com.shift.crm.core.persistence.enities.Transaction;
import com.shift.crm.core.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TransactionImpl implements TransactionService {
    @Override
    public Transaction createTransaction(Long sellerId, CreateTransaction createTransaction) {
        return null;
    }

    @Override
    public Transaction retrieveTransactionDetails(Long transactionId) {
        return null;
    }

    @Override
    public Page<Transaction> retrieveTransactions(PaginationParams params) {
        return null;
    }

    @Override
    public Seller retrieveSellerTransactions(Long sellerId, PaginationParams params) {
        return null;
    }
}
