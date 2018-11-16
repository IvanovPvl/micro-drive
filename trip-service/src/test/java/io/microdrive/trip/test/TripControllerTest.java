package io.microdrive.trip.test;

import io.microdrive.trip.api.domain.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;

import java.util.Date;

import io.microdrive.trip.api.domain.TripInfo;
import io.microdrive.trip.routing.RouteProvider;
import io.microdrive.trip.api.web.TripController;
import io.microdrive.trip.pricing.PriceCalculator;
import io.microdrive.trip.api.data.PointRepository;
import io.microdrive.trip.api.data.TripInfoRepository;

import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebFluxTest(TripController.class)
public class TripControllerTest {

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
        io.microdrive.trip.routing.Point p = new io.microdrive.trip.routing.Point(2.22, 3.33);
        Point point = Point.builder()
                .tripId("tripId")
                .latitude(p.getLatitude())
                .longitude(p.getLongitude())
                .build();

        Point pointUpdated = Point.builder()
                .id("id")
                .tripId(point.getTripId())
                .latitude(p.getLatitude())
                .longitude(p.getLongitude())
                .createdAt(new Date())
                .build();

        given(pointRepo.save(point)).willReturn(Mono.just(pointUpdated));

        String path = String.format("/trip/%s/points", point.getTripId());
        this.webTestClient.post().uri(path)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(p), io.microdrive.trip.routing.Point.class)
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
}
