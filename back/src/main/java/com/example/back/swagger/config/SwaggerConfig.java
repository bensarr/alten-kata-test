package com.example.back.swagger.config;

import com.example.back.swagger.properties.SwaggerInfoProperties;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {
    private final SwaggerInfoProperties openApiProperties;

    public SwaggerConfig(SwaggerInfoProperties openApiProperties) {
        this.openApiProperties = openApiProperties;
    }

    @Bean
    public OpenAPI myOpenAPI() {
        Info info = new Info()
                .title(openApiProperties.getTitle())
                .version(openApiProperties.getVersion())
                .contact(openApiProperties.getContact().toContact())
                .description(openApiProperties.getDescription())
                .termsOfService(openApiProperties.getTermsOfService())
                .license(openApiProperties.getLicense().toLicense());

        return new OpenAPI().info(info).servers(List.of(
                openApiProperties.getServer().toDevServer(),
                openApiProperties.getServer().toTestServer(),
                openApiProperties.getServer().toProdServer()
        ));
    }
}
