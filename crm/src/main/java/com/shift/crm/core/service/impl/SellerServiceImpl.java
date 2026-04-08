package com.shift.crm.core.service.impl;

import com.shift.crm.api.models.requests.CreateSellerRequest;
import com.shift.crm.api.models.requests.PaginationParams;
import com.shift.crm.api.models.requests.UpdateSellerRequest;
import com.shift.crm.core.exceptions.BadRequestException;
import com.shift.crm.core.exceptions.constants.ExceptionMessages;
import com.shift.crm.core.persistence.enities.Seller;
import com.shift.crm.core.persistence.repositories.SellerRepository;
import com.shift.crm.core.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerRepository repository;

    @Override
    public Seller retrieveSellerDetails(Long id) {
        Seller seller = findSellerById(id);
        if(seller == null) {
            throw new NoSuchElementException(ExceptionMessages.SELLER_NOT_FOUND_MSG);
        }
        return seller;
    }

    @Override
    public Seller createSeller(CreateSellerRequest request) {
        Seller newSeller = new Seller();
        newSeller.setName(request.getName());
        newSeller.setContactInfo(request.getContactInfo());

        return save(newSeller);
    }

    @Override
    public Seller updateSeller(Long id, UpdateSellerRequest request) {
        Seller seller = findSellerById(id);
        if(seller == null) {
            throw new NoSuchElementException(ExceptionMessages.SELLER_NOT_FOUND_MSG);
        }

        seller.setName(request.getName());
        seller.setContactInfo(request.getContactInfo());

        return save(seller);
    }

    @Override
    public void deleteSeller(Long id) {
        Seller seller = findSellerById(id);
        if(seller == null) {
            throw new NoSuchElementException(ExceptionMessages.SELLER_NOT_FOUND_MSG);
        }
        seller.setActive(false);

        save(seller);
    }

    @Override
    public Page<Seller> retrieveAll(PaginationParams params) {
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), Sort.by("name").ascending());
        return repository.findAllActive(pageable);
    }

    private boolean isSellerDeleted(Long id) {
        return !repository.isActive(id);
    }

    private Seller findSellerById(Long id) {
        if(repository.existsById(id)) {
            if(isSellerDeleted(id)) {
                throw new BadRequestException(ExceptionMessages.ENTITY_DELETED_MSG);
            }
            return repository.getReferenceById(id);
        }
        return null;
    }

    private Seller save(Seller sellerToSave) {
        return repository.save(sellerToSave);
    }
}
