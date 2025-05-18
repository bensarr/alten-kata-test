package com.example.back.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "admin-auth-filter")
@Configuration
@Data
public class AdminAuthFilterProperties {
    private String email;
    private String path;
    private String message;
}
