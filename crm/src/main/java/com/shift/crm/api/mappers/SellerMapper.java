package com.shift.crm.api.mappers;

import com.shift.crm.api.models.responses.Pagination;
import com.shift.crm.api.models.responses.SellerResponse;
import com.shift.crm.api.models.responses.Sellers;
import com.shift.crm.core.persistence.enities.Seller;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SellerMapper {
    public SellerResponse mapToResponse(Seller seller) {
        return new SellerResponse(
                seller.getId(),
                seller.getName(),
                seller.getContactInfo(),
                seller.getRegistrationDate(),
                seller.getModifiedAt()
        );
    }

    public Sellers mapToSellers(Page<Seller> sellerPage) {
        List<Seller> sellersRaw = sellerPage.getContent();
        List<SellerResponse> sellers = sellersRaw.stream().map(this::mapToResponse).toList();

        int currentPage = sellerPage.getNumber();
        int pages = sellerPage.getTotalPages();
        int size = (int) sellerPage.getTotalElements();

        return new Sellers(sellers, new Pagination(size, pages, currentPage));
    }
 }
