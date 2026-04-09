package com.shift.crm.core.service.impl;

import com.shift.crm.api.models.requests.CreateTransaction;
import com.shift.crm.api.models.requests.PaginationParams;
import com.shift.crm.core.exceptions.constants.ExceptionMessages;
import com.shift.crm.core.persistence.entities.Seller;
import com.shift.crm.core.persistence.entities.Transaction;
import com.shift.crm.core.persistence.repositories.TransactionRepository;
import com.shift.crm.core.service.SellerService;
import com.shift.crm.core.utils.PageableConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTests {

    @Mock
    private TransactionRepository repository;
    @Mock
    private SellerService sellerService;
    @Mock
    private MockedStatic<PageableConverter> pageableConverterMock;

    @InjectMocks
    private TransactionImpl service;

    private Seller seller;
    private Transaction transaction;
    private CreateTransaction createTransaction;
    private PaginationParams params;

    private final Long MOCKED_SELLER_ID = 100L;
    private final String MOCKED_NAME = "John Doe";
    private final String MOCKED_CONTACT_INFO = "johndoe@example.com";
    private final String MOCKED_REGISTER_DATE = "2026-04-10T01:03:15.123";
    private final int MOCKED_SIZE = 5;
    private final int MOCKED_PAGE = 0;

    private final Long MOCKED_TRANSACTION_ID = 1L;
    private final Long MOCKED_AMOUNT = 1000L;
    private final com.shift.crm.core.persistence.enums.PaymentType MOCKED_DB_PAYMENT_TYPE = com.shift.crm.core.persistence.enums.PaymentType.CARD;
    private final com.shift.crm.api.models.enums.PaymentType MOCKED_REQUEST_PAYMENT_TYPE = com.shift.crm.api.models.enums.PaymentType.CARD;

    @BeforeEach
    void setUp() {
        initSeller();
        initTransaction();
        initCreateRequest();
        initPaginationParam();
    }

    private void initSeller() {
        seller = new Seller();
        seller.setId(MOCKED_SELLER_ID);
        seller.setName(MOCKED_NAME);
        seller.setContactInfo(MOCKED_CONTACT_INFO);
        seller.setActive(true);
        seller.setRegistrationDate(LocalDateTime.parse(MOCKED_REGISTER_DATE));
        seller.setModifiedAt(null);
    }

    private void initTransaction() {
        transaction = new Transaction();
        transaction.setId(MOCKED_TRANSACTION_ID);
        transaction.setAmount(MOCKED_AMOUNT);
        transaction.setPaymentType(MOCKED_DB_PAYMENT_TYPE);
        transaction.setSeller(seller);
        transaction.setTransactionDate(LocalDateTime.now());
    }

    private void initCreateRequest() {
        createTransaction = new CreateTransaction();
        createTransaction.setAmount(MOCKED_AMOUNT);
        createTransaction.setPaymentType(MOCKED_REQUEST_PAYMENT_TYPE);
    }

    private void initPaginationParam() {
        params = new PaginationParams(MOCKED_SIZE, MOCKED_PAGE);
    }

    @Test
    void createTransaction_WhenSellerExists_ShouldCreateAndReturnTransaction() {
        when(sellerService.retrieveSellerDetails(MOCKED_SELLER_ID)).thenReturn(seller);
        when(repository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction createdTransaction = service.createTransaction(MOCKED_SELLER_ID, createTransaction);

        assertThat(createdTransaction).isNotNull();
        assertThat(createdTransaction.getId()).isEqualTo(MOCKED_TRANSACTION_ID);
        assertThat(createdTransaction.getAmount()).isEqualTo(MOCKED_AMOUNT);
        assertThat(createdTransaction.getPaymentType()).isEqualTo(MOCKED_DB_PAYMENT_TYPE);
        assertThat(createdTransaction.getSeller()).isEqualTo(seller);

        verify(sellerService).retrieveSellerDetails(MOCKED_SELLER_ID);
        verify(repository).save(any(Transaction.class));
    }

    @Test
    void createTransaction_WhenSellerDoesNotExist_ShouldThrowNoSuchElementException() {
        when(sellerService.retrieveSellerDetails(MOCKED_SELLER_ID))
                .thenThrow(new NoSuchElementException(ExceptionMessages.SELLER_NOT_FOUND_MSG));

        assertThatThrownBy(() -> service.createTransaction(MOCKED_SELLER_ID, createTransaction))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(ExceptionMessages.SELLER_NOT_FOUND_MSG);

        verify(sellerService).retrieveSellerDetails(MOCKED_SELLER_ID);
        verify(repository, never()).save(any(Transaction.class));
    }

    @Test
    void retrieveTransactionDetails_WhenTransactionExists_ShouldReturnTransaction() {
        when(repository.findTransactionById(MOCKED_TRANSACTION_ID)).thenReturn(Optional.of(transaction));

        Transaction foundTransaction = service.retrieveTransactionDetails(MOCKED_TRANSACTION_ID);

        assertThat(foundTransaction).isNotNull();
        assertThat(foundTransaction.getId()).isEqualTo(MOCKED_TRANSACTION_ID);
        assertThat(foundTransaction.getAmount()).isEqualTo(MOCKED_AMOUNT);
        assertThat(foundTransaction.getPaymentType()).isEqualTo(MOCKED_DB_PAYMENT_TYPE);

        verify(repository).findTransactionById(MOCKED_TRANSACTION_ID);
    }

    @Test
    void retrieveTransactionDetails_WhenTransactionDoesNotExist_ShouldThrowNoSuchElementException() {
        when(repository.findTransactionById(101L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.retrieveTransactionDetails(101L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(ExceptionMessages.TRANSACTION_NOT_FOUND_MSG);

        verify(repository).findTransactionById(101L);
    }

    @Test
    void retrieveTransactions_ShouldReturnPageOfTransactions() {

        Pageable expectedPageable = mock(Pageable.class);
        Page<Transaction> expectedPage = new PageImpl<>(Collections.singletonList(transaction));
        final String ORDER_BY = "transactionDate";

        pageableConverterMock.when(() -> PageableConverter.toPageableWithSortDesc(params, ORDER_BY))
                .thenReturn(expectedPageable);

        when(repository.findAll(expectedPageable)).thenReturn(expectedPage);

        Page<Transaction> result = service.retrieveTransactions(params);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotNull();
        assertThat(result.getContent().getFirst()).isEqualTo(transaction);

        pageableConverterMock.verify(() -> PageableConverter.toPageableWithSortDesc(params, ORDER_BY));
        verify(repository).findAll(expectedPageable);
    }

    @Test
    void retrieveSellerTransactions_WhenSellerExists_ShouldReturnPageOfTransactions() {
        Pageable expectedPageable = mock(Pageable.class);
        Page<Transaction> expectedPage = new PageImpl<>(Collections.singletonList(transaction));
        final String ORDER_BY = "transactionDate";

        when(sellerService.retrieveSellerDetails(MOCKED_SELLER_ID)).thenReturn(seller);

        pageableConverterMock.when(() -> PageableConverter.toPageableWithSortDesc(params, ORDER_BY))
                .thenReturn(expectedPageable);

        when(repository.findTransactionBySeller(seller.getId(), expectedPageable)).thenReturn(expectedPage);

        Page<Transaction> result = service.retrieveSellerTransactions(MOCKED_SELLER_ID, params);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotNull();
        assertThat(result.getContent().getFirst()).isEqualTo(transaction);

        verify(sellerService).retrieveSellerDetails(MOCKED_SELLER_ID);
        verify(repository).findTransactionBySeller(seller.getId(), expectedPageable);
        pageableConverterMock.verify(() -> PageableConverter.toPageableWithSortDesc(params, ORDER_BY));
    }

    @Test
    void retrieveSellerTransactions_WhenSellerDoesNotExist_ShouldThrowNoSuchElementException() {
        final Long NON_EXISTING_SELLER_ID = 101L;
        when(sellerService.retrieveSellerDetails(NON_EXISTING_SELLER_ID))
                .thenThrow(new NoSuchElementException(ExceptionMessages.SELLER_NOT_FOUND_MSG));

        assertThatThrownBy(() -> service.retrieveSellerTransactions(NON_EXISTING_SELLER_ID, params))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(ExceptionMessages.SELLER_NOT_FOUND_MSG);

        verify(sellerService).retrieveSellerDetails(NON_EXISTING_SELLER_ID);
        verify(repository, never()).findTransactionBySeller(anyLong(), any(Pageable.class));
    }

    @Test
    void retrieveSellerTransactions_ShouldUseCorrectSellerId() {
        Pageable expectedPageable = mock(Pageable.class);

        when(sellerService.retrieveSellerDetails(MOCKED_SELLER_ID)).thenReturn(seller);

        pageableConverterMock.when(() -> PageableConverter.toPageableWithSortDesc(any(PaginationParams.class), anyString()))
                .thenReturn(expectedPageable);

        when(repository.findTransactionBySeller(anyLong(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        service.retrieveSellerTransactions(MOCKED_SELLER_ID, params);

        verify(repository).findTransactionBySeller(MOCKED_SELLER_ID, expectedPageable);
    }
}