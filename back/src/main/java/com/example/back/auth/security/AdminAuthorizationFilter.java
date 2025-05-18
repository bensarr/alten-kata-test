package com.example.back.auth.security;

import com.example.back.properties.AdminAuthFilterProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter for admin authorization.
 * This filter checks if the authenticated user has the admin email for restricted endpoints.
 */
@Component
@RequiredArgsConstructor
public class AdminAuthorizationFilter extends OncePerRequestFilter {

    private final AdminAuthFilterProperties properties;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        // Check if the request is for a restricted endpoint (product creation, modification, deletion)
        boolean isRestrictedEndpoint = path.startsWith(properties.getPath()) &&
                (HttpMethod.POST.matches(method) || 
                 HttpMethod.PATCH.matches(method) || 
                 HttpMethod.DELETE.matches(method));

        if (isRestrictedEndpoint) {
            // Get the authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication != null && authentication.isAuthenticated()) {
                // Check if the user has the admin email
                String email = authentication.getName();
                
                if (!properties.getEmail().equals(email)) {
                    // User is not authorized to access this endpoint
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write(properties.getMessage());
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}