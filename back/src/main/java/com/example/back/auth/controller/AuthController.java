package com.example.back.auth.controller;

import com.example.back.auth.dto.request.AccountCreateRequestDTO;
import com.example.back.auth.dto.request.TokenRequestDTO;
import com.example.back.auth.dto.response.TokenResponseDTO;
import com.example.back.auth.service.UserService;
import com.example.back.common.dto.ApiDataResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for authentication endpoints.
 * This controller handles account creation and token generation.
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * Create a new user account.
     *
     * @param createDTO the account creation DTO
     * @return a response indicating success or failure
     */
    @PostMapping("/account")
    public ResponseEntity<ApiDataResponse<Void>> createAccount(
        @Valid @RequestBody AccountCreateRequestDTO createDTO) {
        userService.createUser(createDTO);

        ApiDataResponse<Void> response = new ApiDataResponse<>(
                true,
                "Account created successfully",
                null
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Generate a JWT token for a user.
     *
     * @param requestDTO the token request DTO
     * @return a response containing the JWT token
     */
    @PostMapping("/token")
    public ResponseEntity<TokenResponseDTO> generateToken(
        @Valid @RequestBody TokenRequestDTO requestDTO) {
        TokenResponseDTO response = userService.generateLoginToken(requestDTO);
        return ResponseEntity.ok(response);
    }
}
