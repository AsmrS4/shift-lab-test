package com.shift.crm.api.mappers;

import com.shift.crm.api.models.enums.PaymentType;
import com.shift.crm.api.models.responses.Pagination;
import com.shift.crm.api.models.responses.SellerShort;
import com.shift.crm.api.models.responses.TransactionResponse;
import com.shift.crm.api.models.responses.Transactions;
import com.shift.crm.core.persistence.entities.Seller;
import com.shift.crm.core.persistence.entities.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionMapper extends Mapper<Transaction, TransactionResponse, Transactions>{
    @Override
    public TransactionResponse mapToResponse(Transaction entity) {
        PaymentType type = PaymentType.valueOf(entity.getPaymentType().name());
        return new TransactionResponse(
                entity.getId(),
                entity.getAmount(),
                type,
                entity.getTransactionDate(),
                mapToShort(entity.getSeller())
        );
    }

    @Override
    protected Transactions createResponse(List<TransactionResponse> records, Pagination pagination) {
        return new Transactions(records, pagination);
    }

    private SellerShort mapToShort(Seller seller) {
        return new SellerShort(seller.getId(), seller.getName());
    }
}
