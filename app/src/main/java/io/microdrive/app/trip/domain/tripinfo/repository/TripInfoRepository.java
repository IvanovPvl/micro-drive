package io.microdrive.app.trip.domain.tripinfo.repository;

import io.microdrive.app.trip.domain.tripinfo.TripInfo;
import reactor.core.publisher.Mono;

public interface TripInfoRepository {
    Mono<TripInfo> save(TripInfo tripInfo);
    Mono<TripInfo> findOneByDriverIdAndStatus(String driverId, TripInfo.Status status);
    Mono<Boolean> updateFromToStatus(String tripId, TripInfo.Status from, TripInfo.Status to);
    Mono<Boolean> updateFromToStatusForDriver(String tripId, String driverId, TripInfo.Status from, TripInfo.Status to);
}
