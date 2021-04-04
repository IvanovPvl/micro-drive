package io.microdrive.accounts.service;

import io.microdrive.accounts.persistence.Car;
import io.microdrive.accounts.repository.CarRepository;
import io.microdrive.accounts.web.types.CreateAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public Mono<Car> create(CreateAccountRequest.CreateCarRequest request) {
        return carRepository.save(new Car(request));
    }
}
