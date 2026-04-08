package com.shift.crm.api.mappers;

import com.shift.crm.api.models.responses.Pagination;
import com.shift.crm.api.models.responses.SellerResponse;
import com.shift.crm.api.models.responses.SellerStatisticList;
import com.shift.crm.api.models.responses.SellerStatisticResponse;
import com.shift.crm.core.persistence.entities.Seller;
import com.shift.crm.core.persistence.entities.SellerStatistic;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SellerStatisticMapper extends Mapper<SellerStatistic, SellerStatisticResponse, SellerStatisticList> {
    @Override
    public SellerStatisticResponse mapToResponse(SellerStatistic entity) {
        SellerResponse sellerResponse = mapSellerToResponse(entity.getSeller());
        return new SellerStatisticResponse(sellerResponse, entity.getTotalAmount());
    }

    @Override
    protected SellerStatisticList createResponse(List<SellerStatisticResponse> records, Pagination pagination) {
        return new SellerStatisticList(records, pagination);
    }

    private SellerResponse mapSellerToResponse(Seller seller) {
        if(seller == null) {
            return null;
        }
        return new SellerResponse(
                seller.getId(),
                seller.getName(),
                seller.getContactInfo(),
                seller.getRegistrationDate(),
                seller.getModifiedAt()
        );
    }
}
