package io.microdrive.app.routing.tomtom;

import reactor.core.publisher.Mono;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Locale;

import io.microdrive.app.routing.Point;
import io.microdrive.app.routing.RouteInfo;
import io.microdrive.app.routing.RouteProvider;
import io.microdrive.app.routing.tomtom.model.Leg;
import io.microdrive.app.routing.tomtom.model.Route;
import io.microdrive.app.routing.tomtom.model.Summary;
import io.microdrive.app.routing.tomtom.model.CalculateRouteResponse;

@Component
public class TomtomRouteProvider implements RouteProvider {

    private final WebClient tomtomWebClient;
    private final TomtomConfigurationProperties config;

    public TomtomRouteProvider(WebClient tomtomWebClient, TomtomConfigurationProperties config) {
        this.tomtomWebClient = tomtomWebClient;
        this.config = config;
    }

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
