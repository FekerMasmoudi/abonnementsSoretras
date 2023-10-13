package tn.soretras.itineraires.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tn.soretras.itineraires.domain.Itineraire;
import tn.soretras.itineraires.repository.ItineraireRepository;
import tn.soretras.itineraires.service.dto.ItineraireDTO;
import tn.soretras.itineraires.service.mapper.ItineraireMapper;

/**
 * Service Implementation for managing {@link Itineraire}.
 */
@Service
public class ItineraireService {

    private final Logger log = LoggerFactory.getLogger(ItineraireService.class);

    private final ItineraireRepository itineraireRepository;

    private final ItineraireMapper itineraireMapper;

    public ItineraireService(ItineraireRepository itineraireRepository, ItineraireMapper itineraireMapper) {
        this.itineraireRepository = itineraireRepository;
        this.itineraireMapper = itineraireMapper;
    }

    /**
     * Save a itineraire.
     *
     * @param itineraireDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ItineraireDTO> save(ItineraireDTO itineraireDTO) {
        log.debug("Request to save Itineraire : {}", itineraireDTO);
        return itineraireRepository.save(itineraireMapper.toEntity(itineraireDTO)).map(itineraireMapper::toDto);
    }

    /**
     * Update a itineraire.
     *
     * @param itineraireDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ItineraireDTO> update(ItineraireDTO itineraireDTO) {
        log.debug("Request to update Itineraire : {}", itineraireDTO);
        return itineraireRepository.save(itineraireMapper.toEntity(itineraireDTO)).map(itineraireMapper::toDto);
    }

    /**
     * Partially update a itineraire.
     *
     * @param itineraireDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<ItineraireDTO> partialUpdate(ItineraireDTO itineraireDTO) {
        log.debug("Request to partially update Itineraire : {}", itineraireDTO);

        return itineraireRepository
            .findById(itineraireDTO.getId())
            .map(existingItineraire -> {
                itineraireMapper.partialUpdate(existingItineraire, itineraireDTO);

                return existingItineraire;
            })
            .flatMap(itineraireRepository::save)
            .map(itineraireMapper::toDto);
    }

    /**
     * Get all the itineraires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Flux<ItineraireDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Itineraires");
        return itineraireRepository.findAllBy(pageable).map(itineraireMapper::toDto);
    }

    /**
     * Returns the number of itineraires available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return itineraireRepository.count();
    }

    /**
     * Get one itineraire by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Mono<ItineraireDTO> findOne(String id) {
        log.debug("Request to get Itineraire : {}", id);
        return itineraireRepository.findById(id).map(itineraireMapper::toDto);
    }

    /**
     * Delete the itineraire by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Itineraire : {}", id);
        return itineraireRepository.deleteById(id);
    }
}
