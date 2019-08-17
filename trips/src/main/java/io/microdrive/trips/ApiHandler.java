package io.microdrive.trips;

import io.microdrive.core.dto.drivers.Account;
import io.microdrive.core.dto.pricing.PricingRequest;
import io.microdrive.core.dto.routing.RouteRequest;
import io.microdrive.trips.clients.DriversClient;
import io.microdrive.trips.clients.PricingClient;
import io.microdrive.trips.clients.RoutingClient;
import io.microdrive.trips.domain.Trip;
import io.microdrive.trips.dto.ClaimRequest;
import io.microdrive.trips.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
class ApiHandler {

    private final RoutingClient routingClient;
    private final PricingClient pricingClient;
    private final TripService tripService;
    private final DriversClient driversClient;

    Mono<ServerResponse> info(ServerRequest request) {
        val requestMono = request.bodyToMono(RouteRequest.class);
        val userId = request.headers().header("x-user-id").get(0);

        Mono<Trip> tripMono = requestMono.flatMap(routingClient::getRoute)
                .flatMap(routeInfo -> {
                    val pricingRequest = new PricingRequest(routeInfo.getLengthInMeters());
                    return Mono.zip(Mono.just(routeInfo), pricingClient.calculate(pricingRequest));
                }).flatMap(pair -> {
                    val trip = Trip.builder()
                            .routeInfo(pair.getT1())
                            .price(pair.getT2().getPrice())
                            .userId(userId)
                            .status(Trip.Status.NEW)
                            .build();
                    return tripService.addTrip(trip);

                });

        return ok().body(tripMono, Trip.class);
    }

    Mono<ServerResponse> claim(ServerRequest request) {
        // TODO: handle no free drivers
        // TODO: handle trip not found

        val claimRequest = request.bodyToMono(ClaimRequest.class);
        Mono<Account> accountMono = Mono.zip(claimRequest, driversClient.request())
                .flatMap(pair -> {
                    val account = pair.getT2();
                    val result = tripService.claim(pair.getT1().getTripId(), account.getId());
                    return Mono.just(account);
                });

        return ok().body(accountMono, Account.class);
    }

}
