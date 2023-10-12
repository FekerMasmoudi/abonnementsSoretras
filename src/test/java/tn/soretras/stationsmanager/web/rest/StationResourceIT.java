package tn.soretras.stationsmanager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import tn.soretras.stationsmanager.IntegrationTest;
import tn.soretras.stationsmanager.domain.Station;
import tn.soretras.stationsmanager.repository.StationRepository;
import tn.soretras.stationsmanager.service.dto.StationDTO;
import tn.soretras.stationsmanager.service.mapper.StationMapper;

/**
 * Integration tests for the {@link StationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class StationResourceIT {

    private static final String DEFAULT_NAMEFR = "AAAAAAAAAA";
    private static final String UPDATED_NAMEFR = "BBBBBBBBBB";

    private static final String DEFAULT_NAMEAR = "AAAAAAAAAA";
    private static final String UPDATED_NAMEAR = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LATTITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATTITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_DECSTAT = "AAAAAAAAAA";
    private static final String UPDATED_DECSTAT = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/stations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private StationMapper stationMapper;

    @Autowired
    private WebTestClient webTestClient;

    private Station station;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Station createEntity() {
        Station station = new Station()
            .namefr(DEFAULT_NAMEFR)
            .namear(DEFAULT_NAMEAR)
            .longitude(DEFAULT_LONGITUDE)
            .lattitude(DEFAULT_LATTITUDE)
            .decstat(DEFAULT_DECSTAT)
            .status(DEFAULT_STATUS);
        return station;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Station createUpdatedEntity() {
        Station station = new Station()
            .namefr(UPDATED_NAMEFR)
            .namear(UPDATED_NAMEAR)
            .longitude(UPDATED_LONGITUDE)
            .lattitude(UPDATED_LATTITUDE)
            .decstat(UPDATED_DECSTAT)
            .status(UPDATED_STATUS);
        return station;
    }

    @BeforeEach
    public void initTest() {
        stationRepository.deleteAll().block();
        station = createEntity();
    }

    @Test
    void createStation() throws Exception {
        int databaseSizeBeforeCreate = stationRepository.findAll().collectList().block().size();
        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(stationDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll().collectList().block();
        assertThat(stationList).hasSize(databaseSizeBeforeCreate + 1);
        Station testStation = stationList.get(stationList.size() - 1);
        assertThat(testStation.getNamefr()).isEqualTo(DEFAULT_NAMEFR);
        assertThat(testStation.getNamear()).isEqualTo(DEFAULT_NAMEAR);
        assertThat(testStation.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testStation.getLattitude()).isEqualTo(DEFAULT_LATTITUDE);
        assertThat(testStation.getDecstat()).isEqualTo(DEFAULT_DECSTAT);
        assertThat(testStation.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    void createStationWithExistingId() throws Exception {
        // Create the Station with an existing ID
        station.setId("existing_id");
        StationDTO stationDTO = stationMapper.toDto(station);

        int databaseSizeBeforeCreate = stationRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(stationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll().collectList().block();
        assertThat(stationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNamefrIsRequired() throws Exception {
        int databaseSizeBeforeTest = stationRepository.findAll().collectList().block().size();
        // set the field null
        station.setNamefr(null);

        // Create the Station, which fails.
        StationDTO stationDTO = stationMapper.toDto(station);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(stationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Station> stationList = stationRepository.findAll().collectList().block();
        assertThat(stationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNamearIsRequired() throws Exception {
        int databaseSizeBeforeTest = stationRepository.findAll().collectList().block().size();
        // set the field null
        station.setNamear(null);

        // Create the Station, which fails.
        StationDTO stationDTO = stationMapper.toDto(station);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(stationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Station> stationList = stationRepository.findAll().collectList().block();
        assertThat(stationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDecstatIsRequired() throws Exception {
        int databaseSizeBeforeTest = stationRepository.findAll().collectList().block().size();
        // set the field null
        station.setDecstat(null);

        // Create the Station, which fails.
        StationDTO stationDTO = stationMapper.toDto(station);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(stationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Station> stationList = stationRepository.findAll().collectList().block();
        assertThat(stationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = stationRepository.findAll().collectList().block().size();
        // set the field null
        station.setStatus(null);

        // Create the Station, which fails.
        StationDTO stationDTO = stationMapper.toDto(station);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(stationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Station> stationList = stationRepository.findAll().collectList().block();
        assertThat(stationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllStations() {
        // Initialize the database
        stationRepository.save(station).block();

        // Get all the stationList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(station.getId()))
            .jsonPath("$.[*].namefr")
            .value(hasItem(DEFAULT_NAMEFR))
            .jsonPath("$.[*].namear")
            .value(hasItem(DEFAULT_NAMEAR))
            .jsonPath("$.[*].longitude")
            .value(hasItem(DEFAULT_LONGITUDE))
            .jsonPath("$.[*].lattitude")
            .value(hasItem(DEFAULT_LATTITUDE))
            .jsonPath("$.[*].decstat")
            .value(hasItem(DEFAULT_DECSTAT))
            .jsonPath("$.[*].status")
            .value(hasItem(DEFAULT_STATUS));
    }

    @Test
    void getStation() {
        // Initialize the database
        stationRepository.save(station).block();

        // Get the station
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, station.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(station.getId()))
            .jsonPath("$.namefr")
            .value(is(DEFAULT_NAMEFR))
            .jsonPath("$.namear")
            .value(is(DEFAULT_NAMEAR))
            .jsonPath("$.longitude")
            .value(is(DEFAULT_LONGITUDE))
            .jsonPath("$.lattitude")
            .value(is(DEFAULT_LATTITUDE))
            .jsonPath("$.decstat")
            .value(is(DEFAULT_DECSTAT))
            .jsonPath("$.status")
            .value(is(DEFAULT_STATUS));
    }

    @Test
    void getNonExistingStation() {
        // Get the station
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingStation() throws Exception {
        // Initialize the database
        stationRepository.save(station).block();

        int databaseSizeBeforeUpdate = stationRepository.findAll().collectList().block().size();

        // Update the station
        Station updatedStation = stationRepository.findById(station.getId()).block();
        updatedStation
            .namefr(UPDATED_NAMEFR)
            .namear(UPDATED_NAMEAR)
            .longitude(UPDATED_LONGITUDE)
            .lattitude(UPDATED_LATTITUDE)
            .decstat(UPDATED_DECSTAT)
            .status(UPDATED_STATUS);
        StationDTO stationDTO = stationMapper.toDto(updatedStation);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, stationDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(stationDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll().collectList().block();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
        Station testStation = stationList.get(stationList.size() - 1);
        assertThat(testStation.getNamefr()).isEqualTo(UPDATED_NAMEFR);
        assertThat(testStation.getNamear()).isEqualTo(UPDATED_NAMEAR);
        assertThat(testStation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testStation.getLattitude()).isEqualTo(UPDATED_LATTITUDE);
        assertThat(testStation.getDecstat()).isEqualTo(UPDATED_DECSTAT);
        assertThat(testStation.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    void putNonExistingStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().collectList().block().size();
        station.setId(UUID.randomUUID().toString());

        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, stationDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(stationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll().collectList().block();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().collectList().block().size();
        station.setId(UUID.randomUUID().toString());

        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(stationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll().collectList().block();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().collectList().block().size();
        station.setId(UUID.randomUUID().toString());

        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(stationDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll().collectList().block();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateStationWithPatch() throws Exception {
        // Initialize the database
        stationRepository.save(station).block();

        int databaseSizeBeforeUpdate = stationRepository.findAll().collectList().block().size();

        // Update the station using partial update
        Station partialUpdatedStation = new Station();
        partialUpdatedStation.setId(station.getId());

        partialUpdatedStation
            .namefr(UPDATED_NAMEFR)
            .namear(UPDATED_NAMEAR)
            .longitude(UPDATED_LONGITUDE)
            .lattitude(UPDATED_LATTITUDE)
            .status(UPDATED_STATUS);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedStation.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedStation))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll().collectList().block();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
        Station testStation = stationList.get(stationList.size() - 1);
        assertThat(testStation.getNamefr()).isEqualTo(UPDATED_NAMEFR);
        assertThat(testStation.getNamear()).isEqualTo(UPDATED_NAMEAR);
        assertThat(testStation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testStation.getLattitude()).isEqualTo(UPDATED_LATTITUDE);
        assertThat(testStation.getDecstat()).isEqualTo(DEFAULT_DECSTAT);
        assertThat(testStation.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    void fullUpdateStationWithPatch() throws Exception {
        // Initialize the database
        stationRepository.save(station).block();

        int databaseSizeBeforeUpdate = stationRepository.findAll().collectList().block().size();

        // Update the station using partial update
        Station partialUpdatedStation = new Station();
        partialUpdatedStation.setId(station.getId());

        partialUpdatedStation
            .namefr(UPDATED_NAMEFR)
            .namear(UPDATED_NAMEAR)
            .longitude(UPDATED_LONGITUDE)
            .lattitude(UPDATED_LATTITUDE)
            .decstat(UPDATED_DECSTAT)
            .status(UPDATED_STATUS);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedStation.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedStation))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll().collectList().block();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
        Station testStation = stationList.get(stationList.size() - 1);
        assertThat(testStation.getNamefr()).isEqualTo(UPDATED_NAMEFR);
        assertThat(testStation.getNamear()).isEqualTo(UPDATED_NAMEAR);
        assertThat(testStation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testStation.getLattitude()).isEqualTo(UPDATED_LATTITUDE);
        assertThat(testStation.getDecstat()).isEqualTo(UPDATED_DECSTAT);
        assertThat(testStation.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    void patchNonExistingStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().collectList().block().size();
        station.setId(UUID.randomUUID().toString());

        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, stationDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(stationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll().collectList().block();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().collectList().block().size();
        station.setId(UUID.randomUUID().toString());

        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(stationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll().collectList().block();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().collectList().block().size();
        station.setId(UUID.randomUUID().toString());

        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(stationDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll().collectList().block();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteStation() {
        // Initialize the database
        stationRepository.save(station).block();

        int databaseSizeBeforeDelete = stationRepository.findAll().collectList().block().size();

        // Delete the station
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, station.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Station> stationList = stationRepository.findAll().collectList().block();
        assertThat(stationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
