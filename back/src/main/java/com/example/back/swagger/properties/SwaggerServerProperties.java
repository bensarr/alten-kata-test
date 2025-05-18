package com.example.back.swagger.properties;

import io.swagger.v3.oas.models.servers.Server;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "server")
@Configuration
@Data
public class SwaggerServerProperties {
    private String devUrl;
    private String devDescription;

    private String testUrl;
    private String testDescription;

    private String prodUrl;
    private String prodDescription;

    public Server toDevServer(){
        return new Server().url(devUrl).description(devDescription);
    }
    public Server toTestServer(){
        return new Server().url(testUrl).description(testDescription);
    }
    public Server toProdServer(){
        return new Server().url(prodUrl).description(prodDescription);
    }
}
