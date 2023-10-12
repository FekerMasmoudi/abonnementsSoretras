package tn.soretras.stationsmanager.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import tn.soretras.stationsmanager.domain.Station;

/**
 * Spring Data MongoDB reactive repository for the Station entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StationRepository extends ReactiveMongoRepository<Station, String> {
    Flux<Station> findAllBy(Pageable pageable);
}
