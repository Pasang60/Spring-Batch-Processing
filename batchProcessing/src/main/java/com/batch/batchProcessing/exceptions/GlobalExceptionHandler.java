package com.batch.batchProcessing.exceptions;


import com.batch.batchProcessing.common.BaseController;
import com.batch.batchProcessing.exceptions.custom.ResourceNotFoundException;
import com.batch.batchProcessing.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        String errorDetails = String.join(", ", errors);
        return errorResponse("Validation failed", "ERR-400", errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Handle NullPointerException
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponse<Void>> handleNullPointerException(NullPointerException ex) {
        return errorResponse("Null pointer exception occurred", "ERR-500", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return errorResponse("Illegal argument provided", "ERR-402", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handle ResourceNotFoundException (Custom)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return errorResponse(ex.getMessage(), "ERR-404", "The requested resource was not found", HttpStatus.NOT_FOUND);
    }

    // Handle ResponseStatusException
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleResponseStatusException(ResponseStatusException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        return errorResponse(ex.getReason(), "ERR-400", ex.getMessage(), status);
    }

    // Handle HTTP message not readable (e.g., malformed JSON)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return errorResponse("Malformed JSON request", "ERR-400", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handle missing request parameter
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingParam(MissingServletRequestParameterException ex) {
        return errorResponse("Missing request parameter", "ERR-400", ex.getParameterName() + " is missing", HttpStatus.BAD_REQUEST);
    }

    // Handle unsupported HTTP methods
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return errorResponse("HTTP method not supported", "ERR-405", ex.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    // Handle access denied (Spring Security)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(AccessDeniedException ex) {
        return errorResponse("Access denied", "ERR-403", ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    // Handle constraint violations (Bean Validation)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(ConstraintViolationException ex) {
        return errorResponse("Constraint violation", "ERR-400", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handle database errors
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataAccess(DataAccessException ex) {
        return errorResponse("Database error", "ERR-500", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle duplicate key exception
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateKey(DuplicateKeyException ex) {
        return errorResponse("Duplicate entry", "ERR-409", ex.getMessage(), HttpStatus.CONFLICT);
    }

    // Handle when trying to delete non-existent resource
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiResponse<Void>> handleEmptyResult(EmptyResultDataAccessException ex) {
        return errorResponse("No resource found to delete", "ERR-404", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Handle SQL integrity constraint violations
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleSQLIntegrity(SQLIntegrityConstraintViolationException ex) {
        return errorResponse("SQL constraint violation", "ERR-409", ex.getMessage(), HttpStatus.CONFLICT);
    }

    // Handle file upload size exceeded
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<Void>> handleMaxSize(MaxUploadSizeExceededException ex) {
        return errorResponse("File size too large", "ERR-413", ex.getMessage(), HttpStatus.PAYLOAD_TOO_LARGE);
    }

    // Handle transaction errors
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ApiResponse<Void>> handleTransactionException(TransactionSystemException ex) {
        return errorResponse("Transaction failed", "ERR-500", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle message conversion issues
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ApiResponse<Void>> handleConversionException(HttpMessageConversionException ex) {
        return errorResponse("Message conversion error", "ERR-400", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Catch-all exception handler (last resort)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception ex) {
        return errorResponse("An unexpected error occurred", "ERR-500", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
