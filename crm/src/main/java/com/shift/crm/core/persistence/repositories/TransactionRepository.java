package com.shift.crm.core.persistence.repositories;

import com.shift.crm.core.persistence.entities.SellerStatistic;
import com.shift.crm.core.persistence.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findTransactionById(Long id);
    @Query("SELECT t FROM Transaction t WHERE t.seller.id = :sellerId")
    Page<Transaction> findTransactionBySeller(@Param("sellerId") Long sellerId, Pageable pageable);

    @Query("SELECT new com.shift.crm.core.persistence.entities.SellerStatistic(t.seller, SUM(t.amount)) " +
            "FROM Transaction t " +
            "WHERE DATE(t.transactionDate) BETWEEN :dateFrom AND :dateTo " +
            "GROUP BY t.seller ORDER BY SUM(t.amount) DESC")
    List<SellerStatistic> findMostProductive(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);
    @Query("SELECT new com.shift.crm.core.persistence.entities.SellerStatistic(t.seller, SUM(t.amount)) " +
            "FROM Transaction t " +
            "WHERE DATE(t.transactionDate) BETWEEN :dateFrom AND :dateTo " +
            "GROUP BY t.seller HAVING SUM(t.amount) < :amount ORDER BY t.seller.name ASC")
    Page<SellerStatistic> findAllNonProductive(
            @Param("dateFrom")LocalDate dateFrom,
            @Param("dateTo") LocalDate dateTo,
            @Param("amount") Long amount,
            Pageable pageable
    );
}
