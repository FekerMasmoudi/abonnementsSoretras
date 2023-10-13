package tn.soretras.agencesmananger.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tn.soretras.agencesmananger.domain.Agence;
import tn.soretras.agencesmananger.repository.AgenceRepository;
import tn.soretras.agencesmananger.service.dto.AgenceDTO;
import tn.soretras.agencesmananger.service.mapper.AgenceMapper;

/**
 * Service Implementation for managing {@link Agence}.
 */
@Service
public class AgenceService {

    private final Logger log = LoggerFactory.getLogger(AgenceService.class);

    private final AgenceRepository agenceRepository;

    private final AgenceMapper agenceMapper;

    public AgenceService(AgenceRepository agenceRepository, AgenceMapper agenceMapper) {
        this.agenceRepository = agenceRepository;
        this.agenceMapper = agenceMapper;
    }

    /**
     * Save a agence.
     *
     * @param agenceDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<AgenceDTO> save(AgenceDTO agenceDTO) {
        log.debug("Request to save Agence : {}", agenceDTO);
        return agenceRepository.save(agenceMapper.toEntity(agenceDTO)).map(agenceMapper::toDto);
    }

    /**
     * Update a agence.
     *
     * @param agenceDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<AgenceDTO> update(AgenceDTO agenceDTO) {
        log.debug("Request to update Agence : {}", agenceDTO);
        return agenceRepository.save(agenceMapper.toEntity(agenceDTO)).map(agenceMapper::toDto);
    }

    /**
     * Partially update a agence.
     *
     * @param agenceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<AgenceDTO> partialUpdate(AgenceDTO agenceDTO) {
        log.debug("Request to partially update Agence : {}", agenceDTO);

        return agenceRepository
            .findById(agenceDTO.getId())
            .map(existingAgence -> {
                agenceMapper.partialUpdate(existingAgence, agenceDTO);

                return existingAgence;
            })
            .flatMap(agenceRepository::save)
            .map(agenceMapper::toDto);
    }

    /**
     * Get all the agences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Flux<AgenceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Agences");
        return agenceRepository.findAllBy(pageable).map(agenceMapper::toDto);
    }

    /**
     * Returns the number of agences available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return agenceRepository.count();
    }

    /**
     * Get one agence by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Mono<AgenceDTO> findOne(String id) {
        log.debug("Request to get Agence : {}", id);
        return agenceRepository.findById(id).map(agenceMapper::toDto);
    }

    /**
     * Delete the agence by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Agence : {}", id);
        return agenceRepository.deleteById(id);
    }
}
