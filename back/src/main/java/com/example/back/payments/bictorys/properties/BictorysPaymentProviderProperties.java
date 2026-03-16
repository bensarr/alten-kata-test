package com.example.back.payments.bictorys.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "bictorys-payment-provider")
@Configuration
@Data
public class BictorysPaymentProviderProperties {
    private String apiUrl;
    private String publicApiKey;
    private String publicApiValue;
}
