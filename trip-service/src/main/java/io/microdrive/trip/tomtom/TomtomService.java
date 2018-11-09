package io.microdrive.trip.tomtom;

import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.microdrive.trip.tomtom.model.Point;
import io.microdrive.trip.tomtom.model.CalculateRouteResponse;

import java.util.Locale;

@Service
public class TomtomService {

    private final WebClient tomtomWebClient;
    private final TomtomConfigurationProperties config;

    public TomtomService(WebClient tomtomWebClient, TomtomConfigurationProperties config) {
        this.tomtomWebClient = tomtomWebClient;
        this.config = config;
    }

    public Mono<CalculateRouteResponse> calculateRoute(Point from, Point to) {
        String locations = String.format(Locale.US, "%.6f,%.6f:%.6f,%.6f",
                from.getLatitude(),
                from.getLongitude(),
                to.getLatitude(),
                to.getLongitude());

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
                .flatMap(r -> r.bodyToMono(CalculateRouteResponse.class));
    }
}
