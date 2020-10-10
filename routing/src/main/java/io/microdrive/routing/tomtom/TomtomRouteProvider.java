package io.microdrive.routing.tomtom;

import io.microdrive.core.dto.routing.Point;
import io.microdrive.core.dto.routing.RouteInfo;
import io.microdrive.routing.RouteProvider;
import io.microdrive.routing.tomtom.model.CalculateRouteResponse;
import io.microdrive.routing.tomtom.model.Leg;
import io.microdrive.routing.tomtom.model.Route;
import io.microdrive.routing.tomtom.model.Summary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class TomtomRouteProvider implements RouteProvider {
    private final WebClient tomtomWebClient;
    private final TomTomConfigurationProperties config;

    public Mono<RouteInfo> calculateRoute(String locations) {
        String path = String.format("/routing/%s/%s/%s/%s",
                config.getApiVersion(),
                "calculateRoute",
                locations,
                config.getContentType());

        return tomtomWebClient.get()
                .uri(uriBuilder -> uriBuilder.path(path)
                        .queryParam("key", config.getApiKey())
                        .queryParam("travelMode", config.getTravelMode())
                        .build())
                .exchange()
                .flatMap(r -> r.bodyToMono(CalculateRouteResponse.class))
                .map(this::convert);
    }

    public Mono<RouteInfo> calculateRoute(Point from, Point to) {
        String locations = String.format(Locale.US, "%.6f,%.6f:%.6f,%.6f",
                from.getLatitude(),
                from.getLongitude(),
                to.getLatitude(),
                to.getLongitude());

        return calculateRoute(locations);
    }

    private RouteInfo convert(CalculateRouteResponse response) {
        Route route = response.getRoutes().get(0);
        Summary summary = route.getSummary();
        Leg leg = route.getLegs().get(0);

        return RouteInfo.builder()
                .lengthInMeters(summary.getLengthInMeters())
                .trafficDelayInSeconds(summary.getTrafficDelayInSeconds())
                .travelTimeInSeconds(summary.getTravelTimeInSeconds())
                .points(leg.getPoints())
                .build();
    }
}
