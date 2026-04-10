package com.shift.crm.core.service;

import com.shift.crm.api.models.requests.PaginationParams;
import com.shift.crm.api.models.requests.Period;
import com.shift.crm.core.persistence.entities.Seller;
import com.shift.crm.core.persistence.entities.SellerStatistic;
import com.shift.crm.core.persistence.repositories.TransactionRepository;
import com.shift.crm.core.service.impl.TransactionImpl;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatisticServiceImplTests {
    @Mock
    private TransactionRepository repository;
    @Mock
    private MockedStatic<PageableConverter> pageableConverterMock;

    @InjectMocks
    private TransactionImpl transactionService;

    private Period period;
    private Seller seller;
    private SellerStatistic sellerStatistic;
    private PaginationParams params;
    private List<SellerStatistic> statistics;

    @BeforeEach
    public void setUp() {
        initSeller();
        initPeriod();
        initSellerStatistic();
        initSellerStatisticList();
        initPaginationParam();
    }

    private void initPeriod() {
        LocalDateTime now = LocalDateTime.now();
        period = new Period();
        period.setDateFrom(LocalDate.from(now.minusDays(30)));
        period.setDateTo(LocalDate.from(now));
    }

    private void initSeller() {
        seller = new Seller();
        seller.setId(100L);
        seller.setName("John Doe");
        seller.setContactInfo("johndoe@example.com");
        seller.setActive(true);
        seller.setRegistrationDate(LocalDateTime.parse("2026-04-10T01:03:15.123"));
        seller.setModifiedAt(null);
    }

    private void initSellerStatistic() {
        sellerStatistic = new SellerStatistic();
        sellerStatistic.setSeller(seller);
        sellerStatistic.setTotalAmount(15000L);
    }

    private void initSellerStatisticList() {
        Seller sellerFirst = new Seller();
        sellerFirst.setId(1L);
        sellerFirst.setName("First seller");
        sellerFirst.setContactInfo("seller1@example.com");
        sellerFirst.setActive(true);
        sellerFirst.setRegistrationDate(LocalDateTime.now());
        sellerFirst.setModifiedAt(null);

        Seller sellerSecond = new Seller();
        sellerSecond.setId(2L);
        sellerSecond.setName("Second seller");
        sellerSecond.setContactInfo("seller2@example.com");
        sellerSecond.setActive(true);
        sellerSecond.setRegistrationDate(LocalDateTime.now());
        sellerSecond.setModifiedAt(null);

        Seller sellerThird = new Seller();
        sellerThird.setId(3L);
        sellerThird.setName("Third seller");
        sellerThird.setContactInfo("seller3@example.com");
        sellerThird.setActive(true);
        sellerThird.setRegistrationDate(LocalDateTime.now());
        sellerThird.setModifiedAt(null);

        SellerStatistic firstStat = new SellerStatistic(sellerFirst, 20000L);
        SellerStatistic secondStat = new SellerStatistic(sellerSecond, 18000L);
        SellerStatistic thirdStat = new SellerStatistic(sellerThird, 15000L);

        statistics = List.of(firstStat, secondStat, thirdStat);
    }

    private void initPaginationParam() {
        params = new PaginationParams(5, 0);
    }

    @Test
    void findMostProductiveByPeriod_WhenProductiveSellerExists_ShouldReturnFirstElement() {
        when(repository.findMostProductive(period.getDateFrom(), period.getDateTo()))
                .thenReturn(statistics);

        SellerStatistic found = transactionService.findMostProductiveByPeriod(period);

        assertThat(found.getSeller().getId()).isEqualTo(1L);
        assertThat(found.getSeller().getName()).isEqualTo("First seller");
        assertThat(found.getTotalAmount()).isEqualTo(20000L);
    }

    @Test
    void findMostProductiveByPeriod_WhenNoStatisticsExist_ShouldReturnEmptyStatistic() {
        when(repository.findMostProductive(period.getDateFrom(), period.getDateTo()))
                .thenReturn(Collections.emptyList());

        SellerStatistic found = transactionService.findMostProductiveByPeriod(period);

        assertThat(found).isNotNull();
        assertThat(found.getSeller()).isNull();
        assertThat(found.getTotalAmount()).isEqualTo(0L);

        verify(repository).findMostProductive(period.getDateFrom(), period.getDateTo());
    }

    @Test
    void findUnProductiveByPeriodAndAmount_ShouldReturnPageOfStatistics() {
        Long amount = 5000L;
        Pageable expectedPageable = mock(Pageable.class);
        Page<SellerStatistic> expectedPage = new PageImpl<>(List.of(sellerStatistic));

        pageableConverterMock.when(() -> PageableConverter.toPageable(params))
                .thenReturn(expectedPageable);

        when(repository.findAllNonProductive(period.getDateFrom(), period.getDateTo(), amount, expectedPageable))
                .thenReturn(expectedPage);

        Page<SellerStatistic> foundStat = transactionService.findUnProductiveByPeriodAndAmount(period, amount, params);

        assertThat(foundStat).isNotNull();
        assertThat(foundStat.getContent()).isNotNull();
        assertThat(foundStat.getContent().getFirst()).isEqualTo(sellerStatistic);

        pageableConverterMock.verify(() -> PageableConverter.toPageable(params));
        verify(repository).findAllNonProductive(period.getDateFrom(), period.getDateTo(), amount, expectedPageable);
    }

    @Test
    void findUnProductiveByPeriodAndAmount_WhenNoUnproductiveSellers_ShouldReturnEmptyPage() {
        Long amount = 10000L;
        Pageable expectedPageable = mock(Pageable.class);
        Page<SellerStatistic> emptyPage = new PageImpl<>(Collections.emptyList());

        pageableConverterMock.when(() -> PageableConverter.toPageable(params))
                .thenReturn(expectedPageable);

        when(repository.findAllNonProductive(period.getDateFrom(), period.getDateTo(), amount, expectedPageable))
                .thenReturn(emptyPage);

        Page<SellerStatistic> foundStat = transactionService.findUnProductiveByPeriodAndAmount(period, amount, params);

        assertThat(foundStat).isNotNull();
        assertThat(foundStat.getContent().size()).isEqualTo(0);

        verify(repository).findAllNonProductive(period.getDateFrom(), period.getDateTo(), amount, expectedPageable);
    }
}
