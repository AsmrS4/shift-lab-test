package com.shift.crm.core.persistence.repositories;

import com.shift.crm.core.persistence.enities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
