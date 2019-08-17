package io.microdrive.trips.clients;

import io.microdrive.core.dto.pricing.PricingInfo;
import io.microdrive.core.dto.pricing.PricingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
@RequiredArgsConstructor
public class PricingClient {

    private final WebClient.Builder webClientBuilder;

    public Mono<PricingInfo> calculate(PricingRequest request) {
        return webClientBuilder.baseUrl("http://pricing").build().post()
                .uri("/calculate")
                .body(fromObject(request))
                .retrieve()
                .bodyToMono(PricingInfo.class);

    }

}
