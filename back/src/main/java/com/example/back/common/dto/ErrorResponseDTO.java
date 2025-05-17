package com.example.back.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for error responses.
 * This class provides a consistent structure for error responses across the API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {
    
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    
    /**
     * Constructor with all fields except timestamp, which is set to the current time.
     */
    public ErrorResponseDTO(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}