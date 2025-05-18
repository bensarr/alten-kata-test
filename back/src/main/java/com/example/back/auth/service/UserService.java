package com.example.back.auth.service;

import com.example.back.auth.dto.AccountCreateDTO;
import com.example.back.auth.dto.TokenRequestDTO;
import com.example.back.auth.dto.TokenResponseDTO;
import com.example.back.auth.model.User;
import com.example.back.auth.repository.UserRepository;
import com.example.back.auth.security.CustomUserDetails;
import com.example.back.auth.security.JwtUtil;
import com.example.back.common.exception.DuplicateResourceException;
import com.example.back.common.exception.ValidationException;
import com.example.back.auth.properties.AuthMessageProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service class for user management.
 * This class provides methods for creating and finding users.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final AuthMessageProperties properties;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * Generate token for a user.
     *
     * @param requestDTO the token request DTO
     * @return {@link TokenResponseDTO} the authentication  token for the user
     */
    public TokenResponseDTO generateLoginToken(TokenRequestDTO requestDTO) {
        // Find user by email
        Optional<User> userOptional = findUserByEmail(requestDTO.getEmail());

        if (userOptional.isEmpty()) {
            throw new BadCredentialsException(properties.getInvalidCredentials());
        }

        User user = userOptional.get();

        // Verify password
        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException(properties.getInvalidCredentials());
        }

        // Generate token
        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtUtil.generateToken(userDetails);

        return new TokenResponseDTO(token);
    }

    /**
     * Create a new user.
     *
     * @param createDTO the account creation DTO
     * @throws ValidationException        if validation fails
     * @throws DuplicateResourceException if the username or email already exists
     */
    @Transactional
    public void createUser(AccountCreateDTO createDTO) {
        // Validate request
        validateCreateRequest(createDTO);

        // Check if username already exists
        if (userRepository.existsByUsername(createDTO.getUsername())) {
            throw new DuplicateResourceException("User", "username", createDTO.getUsername());
        }

        // Check if email already exists
        if (userRepository.existsByEmail(createDTO.getEmail())) {
            throw new DuplicateResourceException("User", "email", createDTO.getEmail());
        }

        // Create user entity
        User user = new User();
        user.setUsername(createDTO.getUsername());
        user.setFirstname(createDTO.getFirstname());
        user.setEmail(createDTO.getEmail());
        user.setPassword(passwordEncoder.encode(createDTO.getPassword()));

        // Save user
        userRepository.save(user);
    }

    /**
     * Find a user by email.
     *
     * @param email the email
     * @return an Optional containing the user if found, or empty if not found
     */
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Validate an account creation request.
     *
     * @param createDTO the account creation DTO
     * @throws ValidationException if validation fails
     */
    private void validateCreateRequest(AccountCreateDTO createDTO) {
        if (createDTO == null) {
            throw new ValidationException(properties.getAccountCreateNullError());
        }

        if (createDTO.getUsername() == null || createDTO.getUsername().trim().isEmpty()) {
            throw new ValidationException(properties.getUsernameNullError());
        }

        if (createDTO.getFirstname() == null || createDTO.getFirstname().trim().isEmpty()) {
            throw new ValidationException(properties.getFirstnameNullError());
        }

        if (createDTO.getEmail() == null || createDTO.getEmail().trim().isEmpty()) {
            throw new ValidationException(properties.getEmailNullError());
        }

        if (createDTO.getPassword() == null || createDTO.getPassword().trim().isEmpty()) {
            throw new ValidationException(properties.getPasswordNullError());
        }

        if (createDTO.getPassword().length() < 6) {
            throw new ValidationException(properties.getPasswordLengthError());
        }
    }
}