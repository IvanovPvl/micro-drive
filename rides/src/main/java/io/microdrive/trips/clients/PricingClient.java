package io.microdrive.trips.clients;

import io.microdrive.core.dto.pricing.PricingInfo;
import io.microdrive.core.dto.pricing.PricingRequest;
import io.microdrive.trips.config.prising.PricingConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PricingClient {
    private final PricingConfigProperties properties;
    private final WebClient webClient = WebClient.builder().baseUrl(properties.getBaseUrl()).build();

    public Mono<PricingInfo> calculate(PricingRequest request) {
        return webClient
            .post()
            .uri("/calculate")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(PricingInfo.class);
    }
}
