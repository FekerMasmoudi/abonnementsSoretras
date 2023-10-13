package tn.soretras.itineraires.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import tn.soretras.itineraires.domain.Itineraire;

/**
 * Spring Data MongoDB reactive repository for the Itineraire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItineraireRepository extends ReactiveMongoRepository<Itineraire, String> {
    Flux<Itineraire> findAllBy(Pageable pageable);
}
