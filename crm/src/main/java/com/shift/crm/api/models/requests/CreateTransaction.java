package com.shift.crm.api.models.requests;

import com.shift.crm.api.constants.ErrorConstants;
import com.shift.crm.api.models.enums.PaymentType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransaction {
    @Positive(message = ErrorConstants.POSITIVE_VALUE_ERROR_MSG)
    @NotNull(message = ErrorConstants.NOT_BLANK_MSG)
    @Min(value = 1, message = ErrorConstants.MIN_AMOUNT_VALUE)
    @Max(value = 0x7FFFFFFFFFFFFFFFL, message = ErrorConstants.MAX_AMOUNT_VALUE)
    private Long amount;
    @NotNull(message = ErrorConstants.NOT_BLANK_MSG)
    private PaymentType paymentType;
}
