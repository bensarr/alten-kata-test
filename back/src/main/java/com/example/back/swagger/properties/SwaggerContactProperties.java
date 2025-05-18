package com.example.back.swagger.properties;

import io.swagger.v3.oas.models.info.Contact;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "contact")
@Configuration
@Data
public class SwaggerContactProperties {
    private String url;
    private String email;
    private String name;

    public Contact toContact(){
        return new Contact().url(url).email(email).name(name);
    }
}
