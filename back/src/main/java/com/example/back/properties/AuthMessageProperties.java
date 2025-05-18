package com.example.back.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "auth-messages")
@Configuration
@Data
public class AuthMessageProperties {
    private String accountCreateNullError;
    private String invalidCredentials;
    private String usernameNullError;
    private String firstnameNullError;
    private String emailNullError;
    private String passwordNullError;
    private String passwordLengthError;
}
