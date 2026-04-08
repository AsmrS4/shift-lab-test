package com.shift.crm.api.controller;

import com.shift.crm.api.models.requests.CreateTransaction;
import com.shift.crm.api.models.requests.PaginationParams;
import com.shift.crm.api.models.responses.SellerTransactions;
import com.shift.crm.api.models.responses.TransactionResponse;
import com.shift.crm.api.models.responses.Transactions;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    @PostMapping("/seller/{sellerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(@PathVariable("sellerId") Long sellerId, @RequestBody @Valid CreateTransaction createTransaction) {
        return null;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionResponse retrieveTransactionDetails(@PathVariable Long id) {
        return null;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Transactions retrieveTransactions(@ParameterObject @Valid PaginationParams params) {
        return null;
    }

    @GetMapping("/seller/{sellerId}")
    @ResponseStatus(HttpStatus.OK)
    public SellerTransactions retrieveSellerTransactions(@PathVariable("sellerId") Long sellerId, @ParameterObject @Valid PaginationParams params) {
        return null;
    }
}
