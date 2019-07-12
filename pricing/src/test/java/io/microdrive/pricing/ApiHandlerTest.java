package io.microdrive.pricing;

import io.microdrive.pricing.dto.RequestInfo;
import io.microdrive.pricing.dto.ResponseInfo;
import io.microdrive.pricing.service.PriceService;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiHandlerTest {

    @MockBean
    PriceService priceService;

    @Autowired
    RouterFunction<ServerResponse> routerFunction;

    private WebTestClient webClient;

    @Before
    public void setup() {
        webClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    public void calculateShouldReturnRightResponse() {
        val requestInfo = new RequestInfo();
        requestInfo.setDistanceInMeters(1000);

        val price = 10.0;
        when(priceService.calculate(requestInfo)).thenReturn(price);
        val responseInfo = ResponseInfo.builder().price(price).build();

        webClient.post().uri("/calculate")
                .body(Mono.just(requestInfo), RequestInfo.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ResponseInfo.class)
                .isEqualTo(responseInfo);
    }

}
