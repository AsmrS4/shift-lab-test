package com.shift.crm.core.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerStatistic {
    Seller seller;
    Long totalAmount;
}
