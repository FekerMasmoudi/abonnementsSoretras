package tn.soretras.itineraires.web.rest;

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
import tn.soretras.itineraires.repository.ItineraireRepository;
import tn.soretras.itineraires.service.ItineraireService;
import tn.soretras.itineraires.service.dto.ItineraireDTO;
import tn.soretras.itineraires.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.soretras.itineraires.domain.Itineraire}.
 */
@RestController
@RequestMapping("/api")
public class ItineraireResource {

    private final Logger log = LoggerFactory.getLogger(ItineraireResource.class);

    private static final String ENTITY_NAME = "itinerairesdbItineraire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItineraireService itineraireService;

    private final ItineraireRepository itineraireRepository;

    public ItineraireResource(ItineraireService itineraireService, ItineraireRepository itineraireRepository) {
        this.itineraireService = itineraireService;
        this.itineraireRepository = itineraireRepository;
    }

    /**
     * {@code POST  /itineraires} : Create a new itineraire.
     *
     * @param itineraireDTO the itineraireDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itineraireDTO, or with status {@code 400 (Bad Request)} if the itineraire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/itineraires")
    public Mono<ResponseEntity<ItineraireDTO>> createItineraire(@Valid @RequestBody ItineraireDTO itineraireDTO) throws URISyntaxException {
        log.debug("REST request to save Itineraire : {}", itineraireDTO);
        if (itineraireDTO.getId() != null) {
            throw new BadRequestAlertException("A new itineraire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return itineraireService
            .save(itineraireDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/itineraires/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /itineraires/:id} : Updates an existing itineraire.
     *
     * @param id the id of the itineraireDTO to save.
     * @param itineraireDTO the itineraireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itineraireDTO,
     * or with status {@code 400 (Bad Request)} if the itineraireDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itineraireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/itineraires/{id}")
    public Mono<ResponseEntity<ItineraireDTO>> updateItineraire(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ItineraireDTO itineraireDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Itineraire : {}, {}", id, itineraireDTO);
        if (itineraireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itineraireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return itineraireRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return itineraireService
                    .update(itineraireDTO)
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
     * {@code PATCH  /itineraires/:id} : Partial updates given fields of an existing itineraire, field will ignore if it is null
     *
     * @param id the id of the itineraireDTO to save.
     * @param itineraireDTO the itineraireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itineraireDTO,
     * or with status {@code 400 (Bad Request)} if the itineraireDTO is not valid,
     * or with status {@code 404 (Not Found)} if the itineraireDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the itineraireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/itineraires/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<ItineraireDTO>> partialUpdateItineraire(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ItineraireDTO itineraireDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Itineraire partially : {}, {}", id, itineraireDTO);
        if (itineraireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itineraireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return itineraireRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<ItineraireDTO> result = itineraireService.partialUpdate(itineraireDTO);

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
     * {@code GET  /itineraires} : get all the itineraires.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itineraires in body.
     */
    @GetMapping("/itineraires")
    public Mono<ResponseEntity<List<ItineraireDTO>>> getAllItineraires(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Itineraires");
        return itineraireService
            .countAll()
            .zipWith(itineraireService.findAll(pageable).collectList())
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
     * {@code GET  /itineraires/:id} : get the "id" itineraire.
     *
     * @param id the id of the itineraireDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itineraireDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/itineraires/{id}")
    public Mono<ResponseEntity<ItineraireDTO>> getItineraire(@PathVariable String id) {
        log.debug("REST request to get Itineraire : {}", id);
        Mono<ItineraireDTO> itineraireDTO = itineraireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itineraireDTO);
    }

    /**
     * {@code DELETE  /itineraires/:id} : delete the "id" itineraire.
     *
     * @param id the id of the itineraireDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/itineraires/{id}")
    public Mono<ResponseEntity<Void>> deleteItineraire(@PathVariable String id) {
        log.debug("REST request to delete Itineraire : {}", id);
        return itineraireService
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
