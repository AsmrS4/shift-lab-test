package com.shift.crm.api.controller;

import com.shift.crm.api.mappers.Mapper;
import com.shift.crm.api.models.requests.CreateTransaction;
import com.shift.crm.api.models.requests.PaginationParams;
import com.shift.crm.api.models.responses.TransactionResponse;
import com.shift.crm.api.models.responses.Transactions;
import com.shift.crm.core.persistence.enities.Transaction;
import com.shift.crm.core.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;
    private final Mapper<Transaction, TransactionResponse, Transactions> mapper;

    @PostMapping("/seller/{sellerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(@PathVariable("sellerId") Long sellerId, @RequestBody @Valid CreateTransaction createTransaction) {
        Transaction createdTransaction = service.createTransaction(sellerId, createTransaction);
        return mapper.mapToResponse(createdTransaction);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionResponse retrieveTransactionDetails(@PathVariable Long id) {
        Transaction transaction = service.retrieveTransactionDetails(id);
        return mapper.mapToResponse(transaction);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Transactions retrieveTransactions(@ParameterObject @Valid PaginationParams params) {
        Page<Transaction> transactionPage = service.retrieveTransactions(params);
        return mapper.mapToList(transactionPage);
    }

    @GetMapping("/seller/{sellerId}")
    @ResponseStatus(HttpStatus.OK)
    public Transactions retrieveSellerTransactions(@PathVariable("sellerId") Long sellerId, @ParameterObject @Valid PaginationParams params) {
        Page<Transaction> transactionPage = service.retrieveSellerTransactions(sellerId, params);
        return mapper.mapToList(transactionPage);
    }
}
