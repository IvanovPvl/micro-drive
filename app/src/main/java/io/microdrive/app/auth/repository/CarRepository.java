package io.microdrive.app.auth.repository;

import org.springframework.data.repository.CrudRepository;

import io.microdrive.app.auth.domain.Car;

public interface CarRepository extends CrudRepository<Car, Long> {}
