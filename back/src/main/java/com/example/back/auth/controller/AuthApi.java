package com.example.back.auth.controller;

import com.example.back.auth.dto.request.AccountCreateRequestDTO;
import com.example.back.auth.dto.request.TokenRequestDTO;
import com.example.back.auth.dto.response.TokenResponseDTO;
import com.example.back.common.dto.ApiDataResponse;
import com.example.back.common.exception.DuplicateResourceException;
import com.example.back.common.exception.ValidationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Interface defining authentication endpoints with Swagger documentation.
 */
@Tag(name = "Authentication", description = "API for account creation and authentication token generation")
public interface AuthApi {

    /**
     * Endpoint to create a new user account.
     *
     * @param createDTO the account creation data
     * @return a response indicating success or failure
     * @throws ValidationException if the input data fails validation
     * @throws DuplicateResourceException if the username or email already exists
     */
    @Operation(
            summary = "Create a new user account",
            description = "Creates a new user account with the provided information. Validates the input and checks for existing username or email."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Account successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiDataResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid account information",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiDataResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Username or email already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiDataResponse.class))
            )
    })
    @PostMapping("/account")
    ResponseEntity<ApiDataResponse<Void>> createAccount(
            @Parameter(description = "Account creation information", required = true)
            @Valid @RequestBody AccountCreateRequestDTO createDTO);

    /**
     * Endpoint to generate a JWT authentication token.
     *
     * @param requestDTO the login credentials
     * @return a JWT token
     * @throws BadCredentialsException if the credentials are invalid
     */
    @Operation(
            summary = "Generate an authentication token",
            description = "Generates a JWT token for user authentication. Verifies email and password."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Token successfully generated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid authentication information",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication failed - invalid credentials",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping("/token")
    ResponseEntity<TokenResponseDTO> generateToken(
            @Parameter(description = "Login credentials", required = true)
            @Valid @RequestBody TokenRequestDTO requestDTO);
}