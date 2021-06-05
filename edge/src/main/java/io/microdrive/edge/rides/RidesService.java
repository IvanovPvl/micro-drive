package io.microdrive.edge.rides;

import io.microdrive.core.dto.pricing.PricingRequest;
import io.microdrive.core.types.routing.BuildRouteRequest;
import io.microdrive.edge.pricing.PricingProxy;
import io.microdrive.edge.routing.RoutingProxy;
import io.microdrive.edge.types.GetInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.just;
import static reactor.core.publisher.Mono.zip;

@Service
@RequiredArgsConstructor
public class RidesService {
    private final RoutingProxy routingProxy;
    private final PricingProxy pricingProxy;

    public Mono<GetInfoResponse> getInfo(BuildRouteRequest request) {
        return routingProxy
            .buildRoute(request)
            .flatMap(routeInfo -> zip(
                just(routeInfo),
                pricingProxy.calculatePrice(new PricingRequest(routeInfo.lengthInMeters())))
            )
            .flatMap(tuple -> just(new GetInfoResponse(tuple.getT1(), tuple.getT2().getPrice())));
    }
}
