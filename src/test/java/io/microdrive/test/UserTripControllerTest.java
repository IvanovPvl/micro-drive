package io.microdrive.test;

import io.microdrive.pricing.dto.Info;
import io.microdrive.pricing.PriceCalculator;
import io.microdrive.trip.domain.Point;
import io.microdrive.trip.domain.TripInfo;
import io.microdrive.trip.repository.PointRepository;
import io.microdrive.routing.RouteInfo;
import io.microdrive.routing.RouteProvider;
import io.microdrive.trip.web.UserTripController;
import org.junit.Test;
import org.junit.runner.RunWith;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;

import java.util.Date;
import java.util.List;
import java.util.Arrays;

import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebFluxTest(UserTripController.class)
public class UserTripControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TripInfoRepository tripInfoRepo;

    @MockBean
    private RouteProvider routeProvider;

    @MockBean
    private PriceCalculator priceCalculator;

    @MockBean
    private PointRepository pointRepo;

    @Test
    public void getTripInfoById() throws Exception {
        TripInfo tripInfo = TripInfo.builder()
                .id("123")
                .price(1.1)
                .createdAt(new Date())
                .build();

        given(tripInfoRepo.findById(tripInfo.getId())).willReturn(Mono.just(tripInfo));

        String path = String.format("/trip/%s", tripInfo.getId());
        this.webTestClient.get().uri(path).exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isEqualTo(tripInfo.getId())
                .jsonPath("$.price").isEqualTo(tripInfo.getPrice())
                .jsonPath("$.createdAt").isEqualTo(tripInfo.getCreatedAt())
                .jsonPath("$.route").isEmpty();
    }

    @Test
    public void savePointReturnCreatedAndPoint() throws Exception {
        io.microdrive.routing.Point p = new io.microdrive.routing.Point(2.22, 3.33);
        Point point = Point.builder()
                .tripId("tripId")
                .latitude(p.getLatitude())
                .longitude(p.getLongitude())
                .build();

        Point pointUpdated = new Point("id", p.getLatitude(), p.getLongitude(), point.getTripId(), new Date());
        given(pointRepo.save(point)).willReturn(Mono.just(pointUpdated));

        String path = String.format("/trip/%s/points", point.getTripId());
        this.webTestClient.post().uri(path)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(p), io.microdrive.routing.Point.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isEqualTo(pointUpdated.getId())
                .jsonPath("$.tripId").isEqualTo(pointUpdated.getTripId())
                .jsonPath("$.latitude").isEqualTo(pointUpdated.getLatitude())
                .jsonPath("$.longitude").isEqualTo(pointUpdated.getLongitude())
                .jsonPath("$.createdAt").isEqualTo(pointUpdated.getCreatedAt());
    }

    @Test
    public void getPointsForTrip() {
        String tripId = "tripId";
        Point p1 = new Point("1", 1.1, 2.2, tripId, new Date());
        Point p2 = new Point("2", 3.3, 4.4, tripId, new Date());
        List<Point> points = Arrays.asList(p1, p2);
        Flux<Point> pointFlux = Flux.fromIterable(points);
        given(pointRepo.findAllByTripId(tripId)).willReturn(pointFlux);

        String path = String.format("/trip/%s/points", tripId);
        this.webTestClient.get().uri(path).exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.length()").isEqualTo(points.size())
                .jsonPath("$[0].id").isEqualTo(p1.getId())
                .jsonPath("$[0].latitude").isEqualTo(p1.getLatitude())
                .jsonPath("$[0].longitude").isEqualTo(p1.getLongitude())
                .jsonPath("$[0].tripId").isEqualTo(p1.getTripId())
                .jsonPath("$[0].createdAt").isEqualTo(p1.getCreatedAt())
                .jsonPath("$[1].id").isEqualTo(p2.getId())
                .jsonPath("$[1].latitude").isEqualTo(p2.getLatitude())
                .jsonPath("$[1].longitude").isEqualTo(p2.getLongitude())
                .jsonPath("$[1].tripId").isEqualTo(p2.getTripId())
                .jsonPath("$[1].createdAt").isEqualTo(p2.getCreatedAt());
    }

    @Test
    public void getTripInfoByLocations() {
        String locations = "1.1,2.2:3.3,4.4";
        io.microdrive.routing.Point p1 = new io.microdrive.routing.Point(1.1, 2.2);
        io.microdrive.routing.Point p2 = new io.microdrive.routing.Point(3.3, 4.4);
        RouteInfo routeInfo = RouteInfo.builder()
                .lengthInMeters(12000)
                .trafficDelayInSeconds(2)
                .travelTimeInSeconds(4000)
                .points(Arrays.asList(p1, p2))
                .build();

        TripInfo tripInfo = TripInfo.builder()
                .routeInfo(routeInfo)
                .price(100.0)
                .build();

        TripInfo updated = TripInfo.builder()
                .id("id")
                .routeInfo(tripInfo.getRouteInfo())
                .price(tripInfo.getPrice())
                .createdAt(new Date())
                .build();

        given(routeProvider.calculateRoute(locations)).willReturn(Mono.just(routeInfo));
        given(priceCalculator.calculate(new Info(routeInfo.getLengthInMeters()))).willReturn(tripInfo.getPrice());
        given(tripInfoRepo.save(tripInfo)).willReturn(Mono.just(updated));

        String path = String.format("/trip/%s", locations);
        this.webTestClient.post().uri(path).exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .expectBody()
                .jsonPath("$.id").isEqualTo(updated.getId())
                .jsonPath("$.price").isEqualTo(updated.getPrice())
                .jsonPath("$.createdAt").isEqualTo(updated.getCreatedAt())
                .jsonPath("$.route.points.length()").isEqualTo(routeInfo.getPoints().size())
                .jsonPath("$.route.points[0]").isEqualTo(p1)
                .jsonPath("$.route.points[1]").isEqualTo(p2)
                .jsonPath("$.route.lengthInMeters").isEqualTo(routeInfo.getLengthInMeters())
                .jsonPath("$.route.trafficDelayInSeconds").isEqualTo(routeInfo.getTrafficDelayInSeconds())
                .jsonPath("$.route.travelTimeInSeconds").isEqualTo(routeInfo.getTravelTimeInSeconds());
    }
}
