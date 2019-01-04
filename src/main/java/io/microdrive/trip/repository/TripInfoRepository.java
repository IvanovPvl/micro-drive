package io.microdrive.trip.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.microdrive.trip.domain.TripInfo;

public interface TripInfoRepository extends ReactiveMongoRepository<TripInfo, String> {}
