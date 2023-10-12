package tn.soretras.centremanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tn.soretras.centremanager.domain.Center;
import tn.soretras.centremanager.repository.CenterRepository;
import tn.soretras.centremanager.service.dto.CenterDTO;
import tn.soretras.centremanager.service.mapper.CenterMapper;

/**
 * Service Implementation for managing {@link Center}.
 */
@Service
public class CenterService {

    private final Logger log = LoggerFactory.getLogger(CenterService.class);

    private final CenterRepository centerRepository;

    private final CenterMapper centerMapper;

    public CenterService(CenterRepository centerRepository, CenterMapper centerMapper) {
        this.centerRepository = centerRepository;
        this.centerMapper = centerMapper;
    }

    /**
     * Save a center.
     *
     * @param centerDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<CenterDTO> save(CenterDTO centerDTO) {
        log.debug("Request to save Center : {}", centerDTO);
        return centerRepository.save(centerMapper.toEntity(centerDTO)).map(centerMapper::toDto);
    }

    /**
     * Update a center.
     *
     * @param centerDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<CenterDTO> update(CenterDTO centerDTO) {
        log.debug("Request to update Center : {}", centerDTO);
        return centerRepository.save(centerMapper.toEntity(centerDTO)).map(centerMapper::toDto);
    }

    /**
     * Partially update a center.
     *
     * @param centerDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<CenterDTO> partialUpdate(CenterDTO centerDTO) {
        log.debug("Request to partially update Center : {}", centerDTO);

        return centerRepository
            .findById(centerDTO.getId())
            .map(existingCenter -> {
                centerMapper.partialUpdate(existingCenter, centerDTO);

                return existingCenter;
            })
            .flatMap(centerRepository::save)
            .map(centerMapper::toDto);
    }

    /**
     * Get all the centers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Flux<CenterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Centers");
        return centerRepository.findAllBy(pageable).map(centerMapper::toDto);
    }

    /**
     * Returns the number of centers available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return centerRepository.count();
    }

    /**
     * Get one center by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Mono<CenterDTO> findOne(String id) {
        log.debug("Request to get Center : {}", id);
        return centerRepository.findById(id).map(centerMapper::toDto);
    }

    /**
     * Delete the center by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Center : {}", id);
        return centerRepository.deleteById(id);
    }
}
