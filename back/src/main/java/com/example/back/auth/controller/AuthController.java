package com.example.back.auth.controller;

import com.example.back.auth.dto.request.AccountCreateRequestDTO;
import com.example.back.auth.dto.request.TokenRequestDTO;
import com.example.back.auth.dto.response.TokenResponseDTO;
import com.example.back.auth.service.UserService;
import com.example.back.common.dto.ApiDataResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for authentication endpoints.
 * This controller handles account creation and token generation.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController implements AuthApi {

    private final UserService userService;

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<ApiDataResponse<Void>> createAccount(
            @Valid AccountCreateRequestDTO createDTO) {
        log.debug("Creating new user account with username: {}", createDTO.getUsername());
        userService.createUser(createDTO);

        ApiDataResponse<Void> response = new ApiDataResponse<>(
                true,
                "Account created successfully",
                null
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<TokenResponseDTO> generateToken(
            @Valid TokenRequestDTO requestDTO) {
        log.debug("Generating authentication token for email: {}", requestDTO.getEmail());
        TokenResponseDTO response = userService.generateLoginToken(requestDTO);
        return ResponseEntity.ok(response);
    }
}