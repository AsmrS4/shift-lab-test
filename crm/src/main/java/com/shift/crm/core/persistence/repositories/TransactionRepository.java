package com.shift.crm.core.persistence.repositories;

import com.shift.crm.core.persistence.enities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findTransactionById(Long id);
    @Query("SELECT t FROM Transaction t WHERE t.seller.id = :sellerId")
    Page<Transaction> findTransactionBySeller(@Param("sellerId") Long sellerId, Pageable pageable);
}
