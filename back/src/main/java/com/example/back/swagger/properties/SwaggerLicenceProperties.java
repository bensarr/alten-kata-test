package com.example.back.swagger.properties;

import io.swagger.v3.oas.models.info.License;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "licence")
@Configuration
@Data
public class SwaggerLicenceProperties {
    private String url;
    private String name;

    public License toLicense(){
        return new License().url(url).name(name);
    }
}
