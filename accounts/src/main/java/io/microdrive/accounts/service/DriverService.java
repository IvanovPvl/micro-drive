package io.microdrive.accounts.service;

import io.microdrive.accounts.persistence.Car;
import io.microdrive.accounts.persistence.Driver;
import io.microdrive.accounts.repository.CarRepository;
import io.microdrive.accounts.repository.DriverRepository;
import io.microdrive.accounts.web.types.driver.CreateDriverRequest;
import io.microdrive.accounts.web.types.driver.CreateDriverResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;
    private final CarRepository carRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Mono<CreateDriverResponse> create(CreateDriverRequest request) {
        var car = new Car(request.getCar());
        return carRepository.save(car)
            .flatMap(savedCar -> {
                var driver = new Driver(request);
                driver.setCar(savedCar);
                driver.setPassword(passwordEncoder.encode(request.getPassword()));
                return driverRepository.save(driver);
            })
            .map(CreateDriverResponse::new);
    }
}
