package tn.soretras.agencesmananger.web.rest;

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
import tn.soretras.agencesmananger.repository.AgenceRepository;
import tn.soretras.agencesmananger.service.AgenceService;
import tn.soretras.agencesmananger.service.dto.AgenceDTO;
import tn.soretras.agencesmananger.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.soretras.agencesmananger.domain.Agence}.
 */
@RestController
@RequestMapping("/api")
public class AgenceResource {

    private final Logger log = LoggerFactory.getLogger(AgenceResource.class);

    private static final String ENTITY_NAME = "agencesdbAgence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgenceService agenceService;

    private final AgenceRepository agenceRepository;

    public AgenceResource(AgenceService agenceService, AgenceRepository agenceRepository) {
        this.agenceService = agenceService;
        this.agenceRepository = agenceRepository;
    }

    /**
     * {@code POST  /agences} : Create a new agence.
     *
     * @param agenceDTO the agenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agenceDTO, or with status {@code 400 (Bad Request)} if the agence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/agences")
    public Mono<ResponseEntity<AgenceDTO>> createAgence(@Valid @RequestBody AgenceDTO agenceDTO) throws URISyntaxException {
        log.debug("REST request to save Agence : {}", agenceDTO);
        if (agenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new agence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return agenceService
            .save(agenceDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/agences/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /agences/:id} : Updates an existing agence.
     *
     * @param id the id of the agenceDTO to save.
     * @param agenceDTO the agenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agenceDTO,
     * or with status {@code 400 (Bad Request)} if the agenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/agences/{id}")
    public Mono<ResponseEntity<AgenceDTO>> updateAgence(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody AgenceDTO agenceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Agence : {}, {}", id, agenceDTO);
        if (agenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agenceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return agenceRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return agenceService
                    .update(agenceDTO)
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
     * {@code PATCH  /agences/:id} : Partial updates given fields of an existing agence, field will ignore if it is null
     *
     * @param id the id of the agenceDTO to save.
     * @param agenceDTO the agenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agenceDTO,
     * or with status {@code 400 (Bad Request)} if the agenceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the agenceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the agenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/agences/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<AgenceDTO>> partialUpdateAgence(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody AgenceDTO agenceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Agence partially : {}, {}", id, agenceDTO);
        if (agenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agenceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return agenceRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<AgenceDTO> result = agenceService.partialUpdate(agenceDTO);

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
     * {@code GET  /agences} : get all the agences.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agences in body.
     */
    @GetMapping("/agences")
    public Mono<ResponseEntity<List<AgenceDTO>>> getAllAgences(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Agences");
        return agenceService
            .countAll()
            .zipWith(agenceService.findAll(pageable).collectList())
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
     * {@code GET  /agences/:id} : get the "id" agence.
     *
     * @param id the id of the agenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agences/{id}")
    public Mono<ResponseEntity<AgenceDTO>> getAgence(@PathVariable String id) {
        log.debug("REST request to get Agence : {}", id);
        Mono<AgenceDTO> agenceDTO = agenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agenceDTO);
    }

    /**
     * {@code DELETE  /agences/:id} : delete the "id" agence.
     *
     * @param id the id of the agenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agences/{id}")
    public Mono<ResponseEntity<Void>> deleteAgence(@PathVariable String id) {
        log.debug("REST request to delete Agence : {}", id);
        return agenceService
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
