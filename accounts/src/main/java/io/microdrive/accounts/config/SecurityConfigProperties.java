package io.microdrive.accounts.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("security")
public class SecurityConfigProperties {
    private String secret;
    private String alias;
}
