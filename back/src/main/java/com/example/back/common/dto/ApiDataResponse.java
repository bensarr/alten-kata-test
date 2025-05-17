package com.example.back.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic response wrapper for API responses.
 * This class provides a consistent structure for all API responses.
 *
 * @param <T> the type of data contained in the response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiDataResponse<T> {
    private boolean status;
    private String message;
    private T data;
}