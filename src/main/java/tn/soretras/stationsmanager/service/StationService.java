package tn.soretras.stationsmanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tn.soretras.stationsmanager.domain.Station;
import tn.soretras.stationsmanager.repository.StationRepository;
import tn.soretras.stationsmanager.service.dto.StationDTO;
import tn.soretras.stationsmanager.service.mapper.StationMapper;

/**
 * Service Implementation for managing {@link Station}.
 */
@Service
public class StationService {

    private final Logger log = LoggerFactory.getLogger(StationService.class);

    private final StationRepository stationRepository;

    private final StationMapper stationMapper;

    public StationService(StationRepository stationRepository, StationMapper stationMapper) {
        this.stationRepository = stationRepository;
        this.stationMapper = stationMapper;
    }

    /**
     * Save a station.
     *
     * @param stationDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<StationDTO> save(StationDTO stationDTO) {
        log.debug("Request to save Station : {}", stationDTO);
        return stationRepository.save(stationMapper.toEntity(stationDTO)).map(stationMapper::toDto);
    }

    /**
     * Update a station.
     *
     * @param stationDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<StationDTO> update(StationDTO stationDTO) {
        log.debug("Request to update Station : {}", stationDTO);
        return stationRepository.save(stationMapper.toEntity(stationDTO)).map(stationMapper::toDto);
    }

    /**
     * Partially update a station.
     *
     * @param stationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<StationDTO> partialUpdate(StationDTO stationDTO) {
        log.debug("Request to partially update Station : {}", stationDTO);

        return stationRepository
            .findById(stationDTO.getId())
            .map(existingStation -> {
                stationMapper.partialUpdate(existingStation, stationDTO);

                return existingStation;
            })
            .flatMap(stationRepository::save)
            .map(stationMapper::toDto);
    }

    /**
     * Get all the stations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Flux<StationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Stations");
        return stationRepository.findAllBy(pageable).map(stationMapper::toDto);
    }

    /**
     * Returns the number of stations available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return stationRepository.count();
    }

    /**
     * Get one station by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Mono<StationDTO> findOne(String id) {
        log.debug("Request to get Station : {}", id);
        return stationRepository.findById(id).map(stationMapper::toDto);
    }

    /**
     * Delete the station by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Station : {}", id);
        return stationRepository.deleteById(id);
    }
}
