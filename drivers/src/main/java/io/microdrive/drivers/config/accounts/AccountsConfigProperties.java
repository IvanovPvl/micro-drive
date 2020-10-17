package io.microdrive.drivers.config.accounts;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("clients.accounts")
public class AccountsConfigProperties {
    private String baseUrl;
}
