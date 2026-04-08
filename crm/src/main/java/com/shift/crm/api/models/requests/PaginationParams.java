package com.shift.crm.api.models.requests;

import com.shift.crm.api.constants.ErrorConstants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationParams {
    @Positive(message = ErrorConstants.POSITIVE_VALUE_ERROR_MSG)
    @Min(value = 1, message = ErrorConstants.MIN_PAGE_ERROR_MSG)
    private int size;
    @PositiveOrZero(message = ErrorConstants.POSITIVE_OR_ZERO_VALUE_ERROR_MSG)
    @Min(value = 0, message = ErrorConstants.MIN_ELEMENTS_ON_PAGE_SIZE_ERROR_MSG)
    private int page;
}
