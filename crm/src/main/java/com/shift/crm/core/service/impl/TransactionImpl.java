package com.shift.crm.core.service.impl;

import com.shift.crm.api.models.requests.CreateTransaction;
import com.shift.crm.api.models.requests.PaginationParams;
import com.shift.crm.core.exceptions.constants.ExceptionMessages;
import com.shift.crm.core.persistence.enities.Seller;
import com.shift.crm.core.persistence.enities.Transaction;
import com.shift.crm.core.persistence.enums.PaymentType;
import com.shift.crm.core.persistence.repositories.TransactionRepository;
import com.shift.crm.core.service.SellerService;
import com.shift.crm.core.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TransactionImpl implements TransactionService {

    private final TransactionRepository repository;
    private final SellerService sellerService;

    @Override
    @Transactional
    public Transaction createTransaction(Long sellerId, CreateTransaction createTransaction) {
        Seller seller = sellerService.retrieveSellerDetails(sellerId);
        Transaction newTransaction = createTransactionEntity(seller, createTransaction);

        return save(newTransaction);
    }

    @Override
    public Transaction retrieveTransactionDetails(Long transactionId) {
        return findById(transactionId);
    }

    @Override
    public Page<Transaction> retrieveTransactions(PaginationParams params) {
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), Sort.by("transactionDate").descending());
        return repository.findAll(pageable);
    }

    @Override
    public Page<Transaction> retrieveSellerTransactions(Long sellerId, PaginationParams params) {
        Seller seller = sellerService.retrieveSellerDetails(sellerId);
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), Sort.by("transactionDate").descending());
        return repository.findTransactionBySeller(seller.getId(), pageable);
    }

    private Transaction findById(Long transactionId) {
        return repository.findTransactionById(transactionId).orElseThrow(
                () -> new NoSuchElementException(ExceptionMessages.TRANSACTION_NOT_FOUND_MSG)
        );
    }

    private Transaction save(Transaction transactionToSave) {
        return repository.save(transactionToSave);
    }

    private Transaction createTransactionEntity(Seller seller, CreateTransaction createTransaction) {
        PaymentType type = PaymentType.valueOf(createTransaction.getPaymentType().name());
        Transaction newTransaction = new Transaction();

        newTransaction.setAmount(createTransaction.getAmount());
        newTransaction.setPaymentType(type);
        newTransaction.setSeller(seller);

        return newTransaction;
    }
}
