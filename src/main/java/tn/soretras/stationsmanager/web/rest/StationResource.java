package tn.soretras.stationsmanager.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;
import tn.soretras.stationsmanager.repository.StationRepository;
import tn.soretras.stationsmanager.service.StationService;
import tn.soretras.stationsmanager.service.dto.StationDTO;
import tn.soretras.stationsmanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.soretras.stationsmanager.domain.Station}.
 */
@RestController
@RequestMapping("/api")
public class StationResource {

    private final Logger log = LoggerFactory.getLogger(StationResource.class);

    private static final String ENTITY_NAME = "stationsdbStation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StationService stationService;

    private final StationRepository stationRepository;

    public StationResource(StationService stationService, StationRepository stationRepository) {
        this.stationService = stationService;
        this.stationRepository = stationRepository;
    }

    /**
     * {@code POST  /stations} : Create a new station.
     *
     * @param stationDTO the stationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stationDTO, or with status {@code 400 (Bad Request)} if the station has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stations")
    public Mono<ResponseEntity<StationDTO>> createStation(@Valid @RequestBody StationDTO stationDTO) throws URISyntaxException {
        log.debug("REST request to save Station : {}", stationDTO);
        if (stationDTO.getId() != null) {
            throw new BadRequestAlertException("A new station cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return stationService
            .save(stationDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/stations/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /stations/:id} : Updates an existing station.
     *
     * @param id the id of the stationDTO to save.
     * @param stationDTO the stationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stationDTO,
     * or with status {@code 400 (Bad Request)} if the stationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stations/{id}")
    public Mono<ResponseEntity<StationDTO>> updateStation(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody StationDTO stationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Station : {}, {}", id, stationDTO);
        if (stationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return stationRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return stationService
                    .update(stationDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /stations/:id} : Partial updates given fields of an existing station, field will ignore if it is null
     *
     * @param id the id of the stationDTO to save.
     * @param stationDTO the stationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stationDTO,
     * or with status {@code 400 (Bad Request)} if the stationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the stationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the stationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/stations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<StationDTO>> partialUpdateStation(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody StationDTO stationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Station partially : {}, {}", id, stationDTO);
        if (stationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return stationRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<StationDTO> result = stationService.partialUpdate(stationDTO);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, res.getId()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /stations} : get all the stations.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stations in body.
     */
    @GetMapping("/stations")
    public Mono<ResponseEntity<List<StationDTO>>> getAllStations(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Stations");
        return stationService
            .countAll()
            .zipWith(stationService.findAll(pageable).collectList())
            .map(countWithEntities ->
                ResponseEntity
                    .ok()
                    .headers(
                        PaginationUtil.generatePaginationHttpHeaders(
                            UriComponentsBuilder.fromHttpRequest(request),
                            new PageImpl<>(countWithEntities.getT2(), pageable, countWithEntities.getT1())
                        )
                    )
                    .body(countWithEntities.getT2())
            );
    }

    /**
     * {@code GET  /stations/:id} : get the "id" station.
     *
     * @param id the id of the stationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stations/{id}")
    public Mono<ResponseEntity<StationDTO>> getStation(@PathVariable String id) {
        log.debug("REST request to get Station : {}", id);
        Mono<StationDTO> stationDTO = stationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stationDTO);
    }

    /**
     * {@code DELETE  /stations/:id} : delete the "id" station.
     *
     * @param id the id of the stationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stations/{id}")
    public Mono<ResponseEntity<Void>> deleteStation(@PathVariable String id) {
        log.debug("REST request to delete Station : {}", id);
        return stationService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity
                        .noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id))
                        .build()
                )
            );
    }
}
