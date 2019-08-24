package io.microdrive.pricing;

import io.microdrive.core.dto.pricing.PricingInfo;
import io.microdrive.core.dto.pricing.PricingRequest;
import io.microdrive.pricing.service.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
class ApiHandler {

    private final PriceService priceService;

    @NonNull
    Mono<ServerResponse> calculatePrice(ServerRequest request) {
        Mono<PricingInfo> info = request.bodyToMono(PricingRequest.class)
                .map(priceService::calculate)
                .map(price -> PricingInfo.builder().price(price).build());

        return ok().body(info, PricingInfo.class);
    }

}
