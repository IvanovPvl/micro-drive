package io.microdrive.routing.repository;

import io.microdrive.routing.persistence.Route;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RouteRepository extends ReactiveMongoRepository<Route, String> {}
