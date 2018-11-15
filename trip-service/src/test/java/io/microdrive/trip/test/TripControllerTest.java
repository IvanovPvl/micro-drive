package io.microdrive.trip.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import io.microdrive.trip.api.data.TripInfoRepository;
import io.microdrive.trip.api.domain.TripInfo;

import java.util.Date;
import java.text.SimpleDateFormat;

import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TripControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TripInfoRepository tripInfoRepo;

    @Test
    public void getTripInfo() throws Exception {
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
//                .jsonPath("$.createdAt").isEqualTo(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SZ").format(tripInfo.getCreatedAt()))
                .jsonPath("$.createdAt").isNotEmpty()
                .jsonPath("$.route").isEmpty();
    }
}
