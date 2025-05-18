package com.example.back.swagger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "swagger-info")
@Configuration
@Data
public class SwaggerInfoProperties {
    private String title;
    private String version;
    private String description;
    private String termsOfService;
    private SwaggerContactProperties contact;
    private SwaggerLicenceProperties license;
    private SwaggerServerProperties server;
}
