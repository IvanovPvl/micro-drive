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

    public Mono<Car> create(CreateCarRequest request, String accountId) {
        var car = new Car();
        car.setAccountId(accountId);
        car.setColor(request.getColor());
        car.setMark(request.getMark());
        car.setNumber(request.getNumber());
        return carRepository.save(car);
    }

    public Mono<Car> findByAccountId(String accountId) {
        return carRepository.findByAccountId(accountId);
    }
}
