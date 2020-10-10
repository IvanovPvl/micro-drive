package io.microdrive.accounts.service;

import io.microdrive.accounts.persistence.Car;
import io.microdrive.accounts.repository.CarRepository;
import io.microdrive.accounts.web.dto.CreateCarRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    Mono<Car> create(CreateCarRequest request) {
        return carRepository.save(request.toCar());
    }

    Mono<Car> findByAccountId(String accountId) {
        return carRepository.findByAccountId(accountId);
    }
}
