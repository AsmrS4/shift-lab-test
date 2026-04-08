package com.shift.crm.core.exceptions.response;

import java.time.LocalDateTime;
import java.util.Map;

public record ExtendedErrorResponse (String message, LocalDateTime timestamp, Map<String, String> details){
}
