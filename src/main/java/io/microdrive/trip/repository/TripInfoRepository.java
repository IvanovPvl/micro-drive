package io.microdrive.trip.repository;

import com.mongodb.client.result.UpdateResult;

import reactor.core.publisher.Mono;
import io.microdrive.trip.domain.TripInfo;

public interface TripInfoRepository {
    Mono<TripInfo> findByIb(String id);
    Mono<TripInfo> save(TripInfo tripInfo);
    Mono<UpdateResult> updateDriverIdAndStatus(String id, String driverId, TripInfo.Status status);
}
