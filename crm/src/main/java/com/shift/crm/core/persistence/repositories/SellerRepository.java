package com.shift.crm.core.persistence.repositories;

import com.shift.crm.core.persistence.enities.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    @Query("SELECT EXISTS(SELECT 1 FROM Seller s WHERE s.id =:id AND s.isActive = TRUE)")
    boolean isActive(@Param("id") Long id);
    @Query("SELECT s FROM Seller s WHERE s.isActive = TRUE")
    Page<Seller> findAllActive(Pageable pageable);
}
