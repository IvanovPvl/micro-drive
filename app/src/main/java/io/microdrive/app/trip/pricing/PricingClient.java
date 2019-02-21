package io.microdrive.app.trip.pricing;

import reactor.core.publisher.Mono;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import io.microdrive.app.trip.pricing.dto.PricingRequest;
import io.microdrive.app.trip.pricing.dto.PricingResponse;

@Component
@RequiredArgsConstructor
public class PricingClient {

    private final WebClient pricingWebClient;

    public Mono<PricingResponse> calculate(PricingRequest request) {
        return this.pricingWebClient.post()
                .uri("/calculate")
                .body(BodyInserters.fromObject(request))
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(PricingResponse.class));
    }
}
