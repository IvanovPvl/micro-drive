package io.microdrive.edge.pricing;

import io.microdrive.core.dto.pricing.PricingInfo;
import io.microdrive.core.dto.pricing.PricingRequest;
import io.microdrive.edge.pricing.config.PricingConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PricingProxy {
    private final WebClient webClient;
    private final PricingConfigurationProperties properties;

    public Mono<PricingInfo> calculatePrice(PricingRequest request) {
        return webClient.post()
            .uri(properties.getServiceUrl() + "/calculate")
            .bodyValue(request)
            .exchangeToMono(response -> response.bodyToMono(PricingInfo.class));
    }
}
