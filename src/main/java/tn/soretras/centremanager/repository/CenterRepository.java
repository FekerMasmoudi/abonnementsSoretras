package tn.soretras.centremanager.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import tn.soretras.centremanager.domain.Center;

/**
 * Spring Data MongoDB reactive repository for the Center entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CenterRepository extends ReactiveMongoRepository<Center, String> {
    Flux<Center> findAllBy(Pageable pageable);
}
