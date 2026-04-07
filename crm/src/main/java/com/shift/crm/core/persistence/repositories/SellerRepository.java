package com.shift.crm.core.persistence.repositories;

import com.shift.crm.core.persistence.enities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findSellerById(Long id);
    @Query("SELECT EXISTS(SELECT 1 FROM Sellers s WHERE s.isActive = TRUE)")
    boolean isActive(Long id);
}
