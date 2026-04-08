package com.shift.crm.api.models.responses;

import java.time.LocalDateTime;

public record SellerResponse (
        Long id,
        String name,
        String contactInfo,
        LocalDateTime registrationDate,
        LocalDateTime modifiedAt) {
}
