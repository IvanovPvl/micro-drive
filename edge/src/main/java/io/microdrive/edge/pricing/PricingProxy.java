package io.microdrive.edge.pricing;

import io.microdrive.core.types.pricing.PricingInfo;
import io.microdrive.core.types.pricing.PricingRequest;
import io.microdrive.edge.pricing.config.PricingConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PricingProxy {
    private final WebClient webClient;
    private final ReactiveCircuitBreaker pricingCircuitBreaker;
    private final PricingConfigurationProperties properties;

    public Mono<PricingInfo> calculatePrice(PricingRequest request) {
        return webClient.post()
            .uri(properties.getServiceUrl() + "/calculate")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(PricingInfo.class)
            .transform(pricingCircuitBreaker::run);
    }
}
