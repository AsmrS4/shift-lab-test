package com.shift.crm.api.models.requests;

import com.shift.crm.api.constants.ErrorConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSellerRequest {
    @NotBlank(message = ErrorConstants.NOT_BLANK_MSG)
    @Size(min = 3, max = 100, message = ErrorConstants.AVAILABLE_NAME_PARAMETER_SIZE)
    private String name;
    @NotBlank(message = ErrorConstants.NOT_BLANK_MSG)
    @Size(min = 3, max = 100, message = ErrorConstants.AVAILABLE_CONTACT_INFO_PARAMETER_SIZE)
    private String contactInfo;
}
