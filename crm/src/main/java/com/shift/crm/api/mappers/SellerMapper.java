package com.shift.crm.api.mappers;

import com.shift.crm.api.models.responses.Pagination;
import com.shift.crm.api.models.responses.SellerResponse;
import com.shift.crm.api.models.responses.Sellers;
import com.shift.crm.core.persistence.entities.Seller;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SellerMapper extends Mapper<Seller, SellerResponse, Sellers>{
    @Override
    public SellerResponse mapToResponse(Seller seller) {
        return new SellerResponse(
                seller.getId(),
                seller.getName(),
                seller.getContactInfo(),
                seller.getRegistrationDate(),
                seller.getModifiedAt()
        );
    }

    @Override
    protected Sellers createResponse(List<SellerResponse> records, Pagination pagination) {
        return new Sellers(records, pagination);
    }
}
