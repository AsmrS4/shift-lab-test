package com.shift.crm.core.exceptions;

import com.shift.crm.core.exceptions.constants.ExceptionMessages;
import com.shift.crm.core.exceptions.response.ErrorResponse;
import com.shift.crm.core.exceptions.response.ExtendedErrorResponse;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@Hidden
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(responseBody(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
        return new ResponseEntity<>(responseBody(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExtendedErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errorDetails = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement)-> existing + "; " + replacement )
                );
        return new ResponseEntity<>(responseBody(errorDetails), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleCheckedExceptions(Exception ex, HttpServletRequest request) {
        logUnhandledException(ex, request);
        return new ResponseEntity<>(responseBody(ExceptionMessages.INTERNAL_SERVER_ERROR_MSG), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse responseBody(String message) {
        LocalDateTime timestamp = LocalDateTime.now();
        return new ErrorResponse(message, timestamp);
    }
    private ExtendedErrorResponse responseBody(Map<String, String> body) {
        LocalDateTime timestamp = LocalDateTime.now();
        return new ExtendedErrorResponse(ExceptionMessages.VALIDATION_ERROR_MSG, timestamp, body);
    }

    private void logUnhandledException(Exception ex, HttpServletRequest request) {
        log.error("RECEIVED UNHANDLED EXCEPTION: REQUEST {} {}, TRACE: ", request.getMethod(), request.getRequestURI(), ex);
    }
}
