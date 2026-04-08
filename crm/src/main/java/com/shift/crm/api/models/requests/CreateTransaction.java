package com.shift.crm.api.models.requests;

import com.shift.crm.api.constants.ErrorConstants;
import com.shift.crm.api.models.enums.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransaction {
    @Positive(message = ErrorConstants.POSITIVE_VALUE_ERROR_MSG)
    private Integer amount;
    @NotBlank(message = ErrorConstants.NOT_BLANK_MSG)
    private PaymentType paymentType;
}
