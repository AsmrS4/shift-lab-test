package com.shift.crm.api.mappers;

import com.shift.crm.api.models.responses.TransactionResponse;
import com.shift.crm.api.models.responses.Transactions;
import com.shift.crm.core.persistence.enities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper implements Mapper<Transaction, TransactionResponse, Transactions>{
    @Override
    public TransactionResponse mapToResponse(Transaction entity) {
        return null;
    }

    @Override
    public Transactions mapToList(Page<Transaction> entityPage) {
        return null;
    }
}
