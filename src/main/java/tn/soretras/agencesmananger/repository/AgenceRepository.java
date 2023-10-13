package tn.soretras.agencesmananger.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import tn.soretras.agencesmananger.domain.Agence;

/**
 * Spring Data MongoDB reactive repository for the Agence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgenceRepository extends ReactiveMongoRepository<Agence, String> {
    Flux<Agence> findAllBy(Pageable pageable);
}
