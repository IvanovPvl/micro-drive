package io.microdrive.auth.repository;

import org.springframework.data.repository.CrudRepository;

import io.microdrive.auth.domain.Car;

public interface CarRepository extends CrudRepository<Car, Long> {}
