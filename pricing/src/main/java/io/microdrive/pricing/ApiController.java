package io.microdrive.pricing;

import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.microdrive.pricing.dto.Request;
import io.microdrive.pricing.dto.Response;

@RestController
public class ApiController {

    private final PriceCalculator priceCalculator;

    public ApiController(PriceCalculator priceCalculator) {
        this.priceCalculator = priceCalculator;
    }

    @PostMapping("/calculate")
    public Mono<Response> calculate(@RequestBody Request request) {
        return Mono.just(new Response(this.priceCalculator.calculate(request)));
    }
}
