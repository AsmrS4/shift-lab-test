package com.shift.crm.core.exceptions.response;

import java.time.LocalDateTime;

public record ErrorResponse(String message, LocalDateTime timestamp) {}
