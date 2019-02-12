package io.microdrive.app.trip.service;

import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;

import io.microdrive.app.trip.domain.tripinfo.TripInfo;
import io.microdrive.app.trip.domain.tripinfo.repository.TripInfoRepository;

@Service
public class TripService {

    private final TripInfoRepository repository;

    public TripService(TripInfoRepository repository) {
        this.repository = repository;
    }

    /**
     * Assign new trip to driver and set status to NEW
     *
     * @param id tripId
     * @param driverId driverId
     * @return Mono result
     */
    public Mono<Boolean> expectTrip(String id, String driverId) {
        return this.repository.updateFromToStatusForDriver(id, driverId, TripInfo.Status.NEW, TripInfo.Status.EXPECTED);
    }

    /**
     * Start trip
     *
     * @param id tripId
     * @return Mono result
     */
    public Mono<Boolean> startTrip(String id) {
        return this.repository.updateFromToStatus(id, TripInfo.Status.EXPECTED, TripInfo.Status.IN_PROGRESS);
    }

    /**
     * Finish trip
     *
     * @param id tripId
     * @return Mono result
     */
    public Mono<Boolean> finishTrip(String id) {
        return this.repository.updateFromToStatus(id, TripInfo.Status.IN_PROGRESS, TripInfo.Status.FINISHED);
    }

    /**
     * Find expected trip for driver
     *
     * @param driverId driverId
     * @return Mono
     */
    public Mono<TripInfo> findExpectedTripForDriver(String driverId) {
        return this.repository.findOneByDriverIdAndStatus(driverId, TripInfo.Status.EXPECTED);
    }
}
