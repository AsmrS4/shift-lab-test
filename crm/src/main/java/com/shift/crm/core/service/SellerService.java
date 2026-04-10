package com.shift.crm.core.service;

import com.shift.crm.api.models.requests.CreateSellerRequest;
import com.shift.crm.api.models.requests.PaginationParams;
import com.shift.crm.api.models.requests.UpdateSellerRequest;
import com.shift.crm.core.persistence.entities.Seller;
import org.springframework.data.domain.Page;

public interface SellerService {
    Seller retrieveSellerDetails(Long id);
    Seller createSeller(CreateSellerRequest request);
    Seller updateSeller(Long id, UpdateSellerRequest request);
    void deleteSeller(Long id);
    Page<Seller> retrieveAll(PaginationParams params);
}
