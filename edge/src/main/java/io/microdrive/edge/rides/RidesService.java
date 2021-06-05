package io.microdrive.edge.rides;

import io.microdrive.core.Result;
import io.microdrive.core.dto.pricing.PricingRequest;
import io.microdrive.core.types.rides.CreateRideRequest;
import io.microdrive.core.types.routing.BuildRouteRequest;
import io.microdrive.edge.config.security.types.Principal;
import io.microdrive.edge.driverscontrol.DriversControlProxy;
import io.microdrive.edge.pricing.PricingProxy;
import io.microdrive.edge.routing.RoutingProxy;
import io.microdrive.edge.types.GetInfoResponse;
import io.microdrive.edge.types.NewRideRequest;
import io.microdrive.edge.types.NewRideResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static io.microdrive.core.Result.fail;
import static io.microdrive.core.Result.success;
import static org.springframework.security.core.context.ReactiveSecurityContextHolder.getContext;
import static reactor.core.publisher.Mono.just;
import static reactor.core.publisher.Mono.zip;

@Service
@RequiredArgsConstructor
public class RidesService {
    private final RoutingProxy routingProxy;
    private final PricingProxy pricingProxy;
    private final DriversControlProxy driversControlProxy;
    private final RidesProxy ridesProxy;

    public Mono<Result<GetInfoResponse>> getInfo(BuildRouteRequest request) {
        return routingProxy
            .buildRoute(request)
            .flatMap(routeInfo -> zip(
                just(routeInfo),
                pricingProxy.calculatePrice(new PricingRequest(routeInfo.lengthInMeters())))
            )
            .map(tuple -> success(new GetInfoResponse(tuple.getT1(), tuple.getT2().getPrice())))
            .onErrorResume(throwable -> just(fail(throwable)));
    }

    public Mono<Result<NewRideResponse>> create(NewRideRequest request) {
        return getContext()
            .map(securityContext -> (Principal) securityContext.getAuthentication().getPrincipal())
            .flatMap(principal -> zip(just(principal), driversControlProxy.findFree()))
            .flatMap(t -> zip(
                just(t.getT2().driverId()),
                ridesProxy.create(new CreateRideRequest(request.routeId(), t.getT2().driverId(), request.price()), t.getT1()))
            )
            .map(t -> success(new NewRideResponse(t.getT2().id(), t.getT1())))
            .onErrorResume(throwable -> just(fail(throwable)));
    }
}
