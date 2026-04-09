package com.shift.crm.core.service.impl;

import com.shift.crm.api.models.requests.CreateTransaction;
import com.shift.crm.api.models.requests.PaginationParams;
import com.shift.crm.api.models.requests.Period;
import com.shift.crm.core.exceptions.constants.ExceptionMessages;
import com.shift.crm.core.persistence.entities.Seller;
import com.shift.crm.core.persistence.entities.SellerStatistic;
import com.shift.crm.core.persistence.entities.Transaction;
import com.shift.crm.core.persistence.enums.PaymentType;
import com.shift.crm.core.persistence.repositories.TransactionRepository;
import com.shift.crm.core.service.SellerService;
import com.shift.crm.core.service.StatisticService;
import com.shift.crm.core.service.TransactionService;
import com.shift.crm.core.utils.PageableConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TransactionImpl implements TransactionService, StatisticService<SellerStatistic> {

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
        Pageable pageable = PageableConverter.toPageableWithSortDesc(params, "transactionDate");
        return repository.findAll(pageable);
    }

    @Override
    public Page<Transaction> retrieveSellerTransactions(Long sellerId, PaginationParams params) {
        Seller seller = sellerService.retrieveSellerDetails(sellerId);
        Pageable pageable = PageableConverter.toPageableWithSortDesc(params, "transactionDate");
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

    @Override
    public SellerStatistic findMostProductiveByPeriod(Period period) {
        List<SellerStatistic> sellerStatistics = repository.findMostProductive(period.getDateFrom(), period.getDateTo());
        if(sellerStatistics.isEmpty()) {
            return new SellerStatistic(null, 0L);
        }
        return sellerStatistics.getFirst();
    }

    @Override
    public Page<SellerStatistic> findUnProductiveByPeriodAndAmount(Period period, Long amount, PaginationParams params) {
        Pageable pageable = PageableConverter.toPageable(params);
        return repository.findAllNonProductive(period.getDateFrom(), period.getDateTo(), amount, pageable);
    }
}
