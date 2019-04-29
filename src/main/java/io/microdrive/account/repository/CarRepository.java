package io.microdrive.account.repository;

import org.springframework.data.repository.CrudRepository;

import io.microdrive.account.domain.Car;

public interface CarRepository extends CrudRepository<Car, Long> {}
