package com.shift.crm.core.service;

import com.shift.crm.api.models.requests.CreateSellerRequest;
import com.shift.crm.api.models.requests.UpdateSellerRequest;
import com.shift.crm.core.persistence.enities.Seller;
import org.springframework.data.domain.Page;

public interface SellerService {
    Seller retrieveSellerDetails(Long id);
    Seller createSeller(CreateSellerRequest request);
    Seller updateSeller(UpdateSellerRequest request);
    void deleteSeller(Long id);
    Page<Seller> retrieveAll();
}
