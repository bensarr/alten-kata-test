package com.example.back.product.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "product-messages")
@Configuration
@Data
public class ProductMessageProperties {
    private int codeLength;
    private String codePrefix;
    private double minValue;
    private int uuidMinLength;
    private String idNullError;
    private String nameNullError;
    private String nameEmptyError;
    private String negativeValueError;
    private String createNullError;
    private String updateNullError;
}
