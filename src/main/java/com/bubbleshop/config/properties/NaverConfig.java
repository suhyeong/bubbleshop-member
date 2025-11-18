package com.bubbleshop.config.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "naver")
@ToString
public class NaverConfig {
    private String clientId;
    private String authorizeUrl;
    private String loginCallbackUrl;
}
