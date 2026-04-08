package com.shift.crm.api.models.requests;

import com.shift.crm.api.constants.ErrorConstants;
import com.shift.crm.core.exceptions.BadRequestException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Period {
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public Period(LocalDate dateFrom, LocalDate dateTo) {
        if(dateFrom == null || dateTo == null) {
            throw new BadRequestException(ErrorConstants.NULL_PERIOD_PARAMS);
        }
        if(dateFrom.isAfter(dateTo)) {
            throw new BadRequestException(ErrorConstants.INVALID_PERIOD_PARAMS);
        }
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
}
