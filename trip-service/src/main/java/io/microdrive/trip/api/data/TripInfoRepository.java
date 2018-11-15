package io.microdrive.trip.api.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.microdrive.trip.api.domain.TripInfo;

public interface TripInfoRepository extends ReactiveMongoRepository<TripInfo, String> {}
