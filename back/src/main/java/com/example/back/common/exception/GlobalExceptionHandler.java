package com.example.back.common.exception;

import com.example.back.common.dto.ErrorResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Global exception handler for the application.
 * This class provides centralized exception handling across all @RequestMapping methods.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String NOT_FOUND = "Not Found";
    private static final String CONFLICT = "Conflict";
    private static final String BAD_REQUEST = "Bad Request";
    private static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    private static final String DATABASE_CONSTRAINT_VIOLATION = "Database constraint violation";
    private static final String VALIDATION_FAILED = "Validation failed: ";
    private static final String INVALID_PARAMETER_VALUE = "Invalid parameter value: %s";
    private static final String UNEXPECTED_ERROR = "An unexpected error occurred: %s";

    /**
     * Handles ResourceNotFoundException by creating a NOT_FOUND error response.
     *
     * @param ex the exception
     * @param request the HTTP request
     * @return a ResponseEntity containing the error response
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(
            ResourceNotFoundException ex, HttpServletRequest request) {
        return handleException(ex, HttpStatus.NOT_FOUND, NOT_FOUND, request);
    }

    /**
     * Handles EntityNotFoundException by creating a NOT_FOUND error response.
     *
     * @param ex the exception
     * @param request the HTTP request
     * @return a ResponseEntity containing the error response
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEntityNotFoundException(
            EntityNotFoundException ex, HttpServletRequest request) {
        return handleException(ex, HttpStatus.NOT_FOUND, NOT_FOUND, request);
    }

    /**
     * Handles DuplicateResourceException by creating a CONFLICT error response.
     *
     * @param ex the exception
     * @param request the HTTP request
     * @return a ResponseEntity containing the error response
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateResourceException(
            DuplicateResourceException ex, HttpServletRequest request) {
        return handleException(ex, HttpStatus.CONFLICT, CONFLICT, request);
    }

    /**
     * Handles ValidationException by creating a BAD_REQUEST error response.
     *
     * @param ex the exception
     * @param request the HTTP request
     * @return a ResponseEntity containing the error response
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(
            ValidationException ex, HttpServletRequest request) {
        return handleException(ex, HttpStatus.BAD_REQUEST, BAD_REQUEST, request);
    }

    /**
     * Handles DataIntegrityViolationException by creating an error response with a formatted message.
     *
     * @param ex the exception
     * @param request the HTTP request
     * @return a ResponseEntity containing the error response
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex, HttpServletRequest request) {
        String message = String.format("%s: %s", DATABASE_CONSTRAINT_VIOLATION, ex.getMostSpecificCause().getMessage());
        return createErrorResponse(HttpStatus.CONFLICT, CONFLICT, message, request);
    }

    /**
     * Handles MethodArgumentTypeMismatchException by creating an error response with a formatted message.
     *
     * @param ex the exception
     * @param request the HTTP request
     * @return a ResponseEntity containing the error response
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String message = String.format(INVALID_PARAMETER_VALUE, ex.getMessage());
        return createErrorResponse(HttpStatus.BAD_REQUEST, BAD_REQUEST, message, request);
    }

    /**
     * Handles standard exceptions by creating an error response with the exception message.
     *
     * @param ex the exception
     * @param status the HTTP status
     * @param error the error type
     * @param request the HTTP request
     * @return a ResponseEntity containing the error response
     */
    private ResponseEntity<ErrorResponseDTO> handleException(
            Exception ex, HttpStatus status, String error, HttpServletRequest request) {
        return createErrorResponse(status, error, ex.getMessage(), request);
    }

    /**
     * Handles MethodArgumentNotValidException by creating an error response with validation errors.
     * This method extracts field errors from the binding result and formats them into a readable message.
     *
     * @param ex the exception
     * @param request the HTTP request
     * @return a ResponseEntity containing the error response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        String errorMessage = VALIDATION_FAILED + 
                ex.getBindingResult().getAllErrors().stream()
                        .filter(FieldError.class::isInstance)
                        .map(FieldError.class::cast)
                        .map(fieldError -> fieldError.getField() + " - " + 
                                Objects.requireNonNullElse(fieldError.getDefaultMessage(), ""))
                        .collect(Collectors.joining("; "));

        return createErrorResponse(HttpStatus.BAD_REQUEST, BAD_REQUEST, errorMessage, request);
    }

    /**
     * Handles all other exceptions that don't have a specific handler.
     * This is a fallback handler for unexpected exceptions.
     *
     * @param ex the exception
     * @param request the HTTP request
     * @return a ResponseEntity containing the error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(
            Exception ex, HttpServletRequest request) {
        String message = String.format(UNEXPECTED_ERROR, ex.getMessage());
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR, message, request);
    }

    /**
     * Creates an error response with the given status, error, message, and request.
     *
     * @param status the HTTP status
     * @param error the error type
     * @param message the error message
     * @param request the HTTP request
     * @return a ResponseEntity containing the error response
     */
    private ResponseEntity<ErrorResponseDTO> createErrorResponse(
            HttpStatus status, String error, String message, HttpServletRequest request) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                status.value(),
                error,
                message,
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, status);
    }
}
