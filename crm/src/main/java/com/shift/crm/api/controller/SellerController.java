package com.shift.crm.api.controller;

import com.shift.crm.api.mappers.SellerMapper;
import com.shift.crm.api.models.requests.CreateSellerRequest;
import com.shift.crm.api.models.requests.PaginationParams;
import com.shift.crm.api.models.requests.UpdateSellerRequest;
import com.shift.crm.api.models.responses.SellerResponse;
import com.shift.crm.api.models.responses.Sellers;
import com.shift.crm.core.persistence.enities.Seller;
import com.shift.crm.core.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
public class SellerController {
    private final SellerService service;
    private final SellerMapper mapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Sellers retrieveSellers(@ParameterObject @Valid PaginationParams params) {
        Page<Seller> sellers = service.retrieveAll(params);
        return mapper.mapToSellers(sellers);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SellerResponse retrieveSellerDetails(@PathVariable Long id) {
        Seller seller = service.retrieveSellerDetails(id);
        return mapper.mapToResponse(seller);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SellerResponse createSeller(@RequestBody @Valid CreateSellerRequest createRequest) {
        Seller createdSeller = service.createSeller(createRequest);
        return mapper.mapToResponse(createdSeller);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SellerResponse updateSellerInfo(@PathVariable Long id, @RequestBody @Valid UpdateSellerRequest updateRequest) {
        Seller updatedSeller = service.updateSeller(id, updateRequest);
        return mapper.mapToResponse(updatedSeller);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSeller(@PathVariable Long id) {
        service.deleteSeller(id);
    }
}
