package com.shift.crm.api.models.responses;

import com.shift.crm.api.models.enums.PaymentType;

import java.time.LocalDateTime;

public record TransactionResponse(Long id, Long amount, PaymentType paymentType, LocalDateTime transactionDate, SellerShort producer) {
}
