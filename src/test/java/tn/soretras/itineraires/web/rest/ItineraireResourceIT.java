package tn.soretras.itineraires.web.rest;

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
import tn.soretras.itineraires.IntegrationTest;
import tn.soretras.itineraires.domain.Itineraire;
import tn.soretras.itineraires.repository.ItineraireRepository;
import tn.soretras.itineraires.service.dto.ItineraireDTO;
import tn.soretras.itineraires.service.mapper.ItineraireMapper;

/**
 * Integration tests for the {@link ItineraireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ItineraireResourceIT {

    private static final Integer DEFAULT_DECCENT = 1;
    private static final Integer UPDATED_DECCENT = 2;

    private static final Integer DEFAULT_DECAGENC = 1;
    private static final Integer UPDATED_DECAGENC = 2;

    private static final String DEFAULT_DENUMLI = "AAAAAAAAAA";
    private static final String UPDATED_DENUMLI = "BBBBBBBBBB";

    private static final String DEFAULT_DECSTAT = "AAAAAAAAAA";
    private static final String UPDATED_DECSTAT = "BBBBBBBBBB";

    private static final Integer DEFAULT_DENUMLG = 1;
    private static final Integer UPDATED_DENUMLG = 2;

    private static final Integer DEFAULT_DEKMSTA = 1;
    private static final Integer UPDATED_DEKMSTA = 2;

    private static final Integer DEFAULT_DEDURTR = 1;
    private static final Integer UPDATED_DEDURTR = 2;

    private static final Integer DEFAULT_DEESCALE = 1;
    private static final Integer UPDATED_DEESCALE = 2;

    private static final String DEFAULT_EMBRA = "AAAAAAAAAA";
    private static final String UPDATED_EMBRA = "BBBBBBBBBB";

    private static final Integer DEFAULT_SECTION = 1;
    private static final Integer UPDATED_SECTION = 2;

    private static final String DEFAULT_SENS = "AAAAAAAAAA";
    private static final String UPDATED_SENS = "BBBBBBBBBB";

    private static final String DEFAULT_DECTYST = "AAAAAAAAAA";
    private static final String UPDATED_DECTYST = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/itineraires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ItineraireRepository itineraireRepository;

    @Autowired
    private ItineraireMapper itineraireMapper;

    @Autowired
    private WebTestClient webTestClient;

    private Itineraire itineraire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Itineraire createEntity() {
        Itineraire itineraire = new Itineraire()
            .deccent(DEFAULT_DECCENT)
            .decagenc(DEFAULT_DECAGENC)
            .denumli(DEFAULT_DENUMLI)
            .decstat(DEFAULT_DECSTAT)
            .denumlg(DEFAULT_DENUMLG)
            .dekmsta(DEFAULT_DEKMSTA)
            .dedurtr(DEFAULT_DEDURTR)
            .deescale(DEFAULT_DEESCALE)
            .embra(DEFAULT_EMBRA)
            .section(DEFAULT_SECTION)
            .sens(DEFAULT_SENS)
            .dectyst(DEFAULT_DECTYST);
        return itineraire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Itineraire createUpdatedEntity() {
        Itineraire itineraire = new Itineraire()
            .deccent(UPDATED_DECCENT)
            .decagenc(UPDATED_DECAGENC)
            .denumli(UPDATED_DENUMLI)
            .decstat(UPDATED_DECSTAT)
            .denumlg(UPDATED_DENUMLG)
            .dekmsta(UPDATED_DEKMSTA)
            .dedurtr(UPDATED_DEDURTR)
            .deescale(UPDATED_DEESCALE)
            .embra(UPDATED_EMBRA)
            .section(UPDATED_SECTION)
            .sens(UPDATED_SENS)
            .dectyst(UPDATED_DECTYST);
        return itineraire;
    }

    @BeforeEach
    public void initTest() {
        itineraireRepository.deleteAll().block();
        itineraire = createEntity();
    }

    @Test
    void createItineraire() throws Exception {
        int databaseSizeBeforeCreate = itineraireRepository.findAll().collectList().block().size();
        // Create the Itineraire
        ItineraireDTO itineraireDTO = itineraireMapper.toDto(itineraire);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(itineraireDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Itineraire in the database
        List<Itineraire> itineraireList = itineraireRepository.findAll().collectList().block();
        assertThat(itineraireList).hasSize(databaseSizeBeforeCreate + 1);
        Itineraire testItineraire = itineraireList.get(itineraireList.size() - 1);
        assertThat(testItineraire.getDeccent()).isEqualTo(DEFAULT_DECCENT);
        assertThat(testItineraire.getDecagenc()).isEqualTo(DEFAULT_DECAGENC);
        assertThat(testItineraire.getDenumli()).isEqualTo(DEFAULT_DENUMLI);
        assertThat(testItineraire.getDecstat()).isEqualTo(DEFAULT_DECSTAT);
        assertThat(testItineraire.getDenumlg()).isEqualTo(DEFAULT_DENUMLG);
        assertThat(testItineraire.getDekmsta()).isEqualTo(DEFAULT_DEKMSTA);
        assertThat(testItineraire.getDedurtr()).isEqualTo(DEFAULT_DEDURTR);
        assertThat(testItineraire.getDeescale()).isEqualTo(DEFAULT_DEESCALE);
        assertThat(testItineraire.getEmbra()).isEqualTo(DEFAULT_EMBRA);
        assertThat(testItineraire.getSection()).isEqualTo(DEFAULT_SECTION);
        assertThat(testItineraire.getSens()).isEqualTo(DEFAULT_SENS);
        assertThat(testItineraire.getDectyst()).isEqualTo(DEFAULT_DECTYST);
    }

    @Test
    void createItineraireWithExistingId() throws Exception {
        // Create the Itineraire with an existing ID
        itineraire.setId("existing_id");
        ItineraireDTO itineraireDTO = itineraireMapper.toDto(itineraire);

        int databaseSizeBeforeCreate = itineraireRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(itineraireDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Itineraire in the database
        List<Itineraire> itineraireList = itineraireRepository.findAll().collectList().block();
        assertThat(itineraireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkDecagencIsRequired() throws Exception {
        int databaseSizeBeforeTest = itineraireRepository.findAll().collectList().block().size();
        // set the field null
        itineraire.setDecagenc(null);

        // Create the Itineraire, which fails.
        ItineraireDTO itineraireDTO = itineraireMapper.toDto(itineraire);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(itineraireDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Itineraire> itineraireList = itineraireRepository.findAll().collectList().block();
        assertThat(itineraireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDenumliIsRequired() throws Exception {
        int databaseSizeBeforeTest = itineraireRepository.findAll().collectList().block().size();
        // set the field null
        itineraire.setDenumli(null);

        // Create the Itineraire, which fails.
        ItineraireDTO itineraireDTO = itineraireMapper.toDto(itineraire);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(itineraireDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Itineraire> itineraireList = itineraireRepository.findAll().collectList().block();
        assertThat(itineraireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDecstatIsRequired() throws Exception {
        int databaseSizeBeforeTest = itineraireRepository.findAll().collectList().block().size();
        // set the field null
        itineraire.setDecstat(null);

        // Create the Itineraire, which fails.
        ItineraireDTO itineraireDTO = itineraireMapper.toDto(itineraire);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(itineraireDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Itineraire> itineraireList = itineraireRepository.findAll().collectList().block();
        assertThat(itineraireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDenumlgIsRequired() throws Exception {
        int databaseSizeBeforeTest = itineraireRepository.findAll().collectList().block().size();
        // set the field null
        itineraire.setDenumlg(null);

        // Create the Itineraire, which fails.
        ItineraireDTO itineraireDTO = itineraireMapper.toDto(itineraire);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(itineraireDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Itineraire> itineraireList = itineraireRepository.findAll().collectList().block();
        assertThat(itineraireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllItineraires() {
        // Initialize the database
        itineraireRepository.save(itineraire).block();

        // Get all the itineraireList
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
            .value(hasItem(itineraire.getId()))
            .jsonPath("$.[*].deccent")
            .value(hasItem(DEFAULT_DECCENT))
            .jsonPath("$.[*].decagenc")
            .value(hasItem(DEFAULT_DECAGENC))
            .jsonPath("$.[*].denumli")
            .value(hasItem(DEFAULT_DENUMLI))
            .jsonPath("$.[*].decstat")
            .value(hasItem(DEFAULT_DECSTAT))
            .jsonPath("$.[*].denumlg")
            .value(hasItem(DEFAULT_DENUMLG))
            .jsonPath("$.[*].dekmsta")
            .value(hasItem(DEFAULT_DEKMSTA))
            .jsonPath("$.[*].dedurtr")
            .value(hasItem(DEFAULT_DEDURTR))
            .jsonPath("$.[*].deescale")
            .value(hasItem(DEFAULT_DEESCALE))
            .jsonPath("$.[*].embra")
            .value(hasItem(DEFAULT_EMBRA))
            .jsonPath("$.[*].section")
            .value(hasItem(DEFAULT_SECTION))
            .jsonPath("$.[*].sens")
            .value(hasItem(DEFAULT_SENS))
            .jsonPath("$.[*].dectyst")
            .value(hasItem(DEFAULT_DECTYST));
    }

    @Test
    void getItineraire() {
        // Initialize the database
        itineraireRepository.save(itineraire).block();

        // Get the itineraire
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, itineraire.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(itineraire.getId()))
            .jsonPath("$.deccent")
            .value(is(DEFAULT_DECCENT))
            .jsonPath("$.decagenc")
            .value(is(DEFAULT_DECAGENC))
            .jsonPath("$.denumli")
            .value(is(DEFAULT_DENUMLI))
            .jsonPath("$.decstat")
            .value(is(DEFAULT_DECSTAT))
            .jsonPath("$.denumlg")
            .value(is(DEFAULT_DENUMLG))
            .jsonPath("$.dekmsta")
            .value(is(DEFAULT_DEKMSTA))
            .jsonPath("$.dedurtr")
            .value(is(DEFAULT_DEDURTR))
            .jsonPath("$.deescale")
            .value(is(DEFAULT_DEESCALE))
            .jsonPath("$.embra")
            .value(is(DEFAULT_EMBRA))
            .jsonPath("$.section")
            .value(is(DEFAULT_SECTION))
            .jsonPath("$.sens")
            .value(is(DEFAULT_SENS))
            .jsonPath("$.dectyst")
            .value(is(DEFAULT_DECTYST));
    }

    @Test
    void getNonExistingItineraire() {
        // Get the itineraire
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingItineraire() throws Exception {
        // Initialize the database
        itineraireRepository.save(itineraire).block();

        int databaseSizeBeforeUpdate = itineraireRepository.findAll().collectList().block().size();

        // Update the itineraire
        Itineraire updatedItineraire = itineraireRepository.findById(itineraire.getId()).block();
        updatedItineraire
            .deccent(UPDATED_DECCENT)
            .decagenc(UPDATED_DECAGENC)
            .denumli(UPDATED_DENUMLI)
            .decstat(UPDATED_DECSTAT)
            .denumlg(UPDATED_DENUMLG)
            .dekmsta(UPDATED_DEKMSTA)
            .dedurtr(UPDATED_DEDURTR)
            .deescale(UPDATED_DEESCALE)
            .embra(UPDATED_EMBRA)
            .section(UPDATED_SECTION)
            .sens(UPDATED_SENS)
            .dectyst(UPDATED_DECTYST);
        ItineraireDTO itineraireDTO = itineraireMapper.toDto(updatedItineraire);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, itineraireDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(itineraireDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Itineraire in the database
        List<Itineraire> itineraireList = itineraireRepository.findAll().collectList().block();
        assertThat(itineraireList).hasSize(databaseSizeBeforeUpdate);
        Itineraire testItineraire = itineraireList.get(itineraireList.size() - 1);
        assertThat(testItineraire.getDeccent()).isEqualTo(UPDATED_DECCENT);
        assertThat(testItineraire.getDecagenc()).isEqualTo(UPDATED_DECAGENC);
        assertThat(testItineraire.getDenumli()).isEqualTo(UPDATED_DENUMLI);
        assertThat(testItineraire.getDecstat()).isEqualTo(UPDATED_DECSTAT);
        assertThat(testItineraire.getDenumlg()).isEqualTo(UPDATED_DENUMLG);
        assertThat(testItineraire.getDekmsta()).isEqualTo(UPDATED_DEKMSTA);
        assertThat(testItineraire.getDedurtr()).isEqualTo(UPDATED_DEDURTR);
        assertThat(testItineraire.getDeescale()).isEqualTo(UPDATED_DEESCALE);
        assertThat(testItineraire.getEmbra()).isEqualTo(UPDATED_EMBRA);
        assertThat(testItineraire.getSection()).isEqualTo(UPDATED_SECTION);
        assertThat(testItineraire.getSens()).isEqualTo(UPDATED_SENS);
        assertThat(testItineraire.getDectyst()).isEqualTo(UPDATED_DECTYST);
    }

    @Test
    void putNonExistingItineraire() throws Exception {
        int databaseSizeBeforeUpdate = itineraireRepository.findAll().collectList().block().size();
        itineraire.setId(UUID.randomUUID().toString());

        // Create the Itineraire
        ItineraireDTO itineraireDTO = itineraireMapper.toDto(itineraire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, itineraireDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(itineraireDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Itineraire in the database
        List<Itineraire> itineraireList = itineraireRepository.findAll().collectList().block();
        assertThat(itineraireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchItineraire() throws Exception {
        int databaseSizeBeforeUpdate = itineraireRepository.findAll().collectList().block().size();
        itineraire.setId(UUID.randomUUID().toString());

        // Create the Itineraire
        ItineraireDTO itineraireDTO = itineraireMapper.toDto(itineraire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(itineraireDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Itineraire in the database
        List<Itineraire> itineraireList = itineraireRepository.findAll().collectList().block();
        assertThat(itineraireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamItineraire() throws Exception {
        int databaseSizeBeforeUpdate = itineraireRepository.findAll().collectList().block().size();
        itineraire.setId(UUID.randomUUID().toString());

        // Create the Itineraire
        ItineraireDTO itineraireDTO = itineraireMapper.toDto(itineraire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(itineraireDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Itineraire in the database
        List<Itineraire> itineraireList = itineraireRepository.findAll().collectList().block();
        assertThat(itineraireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateItineraireWithPatch() throws Exception {
        // Initialize the database
        itineraireRepository.save(itineraire).block();

        int databaseSizeBeforeUpdate = itineraireRepository.findAll().collectList().block().size();

        // Update the itineraire using partial update
        Itineraire partialUpdatedItineraire = new Itineraire();
        partialUpdatedItineraire.setId(itineraire.getId());

        partialUpdatedItineraire
            .decagenc(UPDATED_DECAGENC)
            .denumli(UPDATED_DENUMLI)
            .denumlg(UPDATED_DENUMLG)
            .deescale(UPDATED_DEESCALE)
            .embra(UPDATED_EMBRA)
            .section(UPDATED_SECTION)
            .sens(UPDATED_SENS)
            .dectyst(UPDATED_DECTYST);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedItineraire.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedItineraire))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Itineraire in the database
        List<Itineraire> itineraireList = itineraireRepository.findAll().collectList().block();
        assertThat(itineraireList).hasSize(databaseSizeBeforeUpdate);
        Itineraire testItineraire = itineraireList.get(itineraireList.size() - 1);
        assertThat(testItineraire.getDeccent()).isEqualTo(DEFAULT_DECCENT);
        assertThat(testItineraire.getDecagenc()).isEqualTo(UPDATED_DECAGENC);
        assertThat(testItineraire.getDenumli()).isEqualTo(UPDATED_DENUMLI);
        assertThat(testItineraire.getDecstat()).isEqualTo(DEFAULT_DECSTAT);
        assertThat(testItineraire.getDenumlg()).isEqualTo(UPDATED_DENUMLG);
        assertThat(testItineraire.getDekmsta()).isEqualTo(DEFAULT_DEKMSTA);
        assertThat(testItineraire.getDedurtr()).isEqualTo(DEFAULT_DEDURTR);
        assertThat(testItineraire.getDeescale()).isEqualTo(UPDATED_DEESCALE);
        assertThat(testItineraire.getEmbra()).isEqualTo(UPDATED_EMBRA);
        assertThat(testItineraire.getSection()).isEqualTo(UPDATED_SECTION);
        assertThat(testItineraire.getSens()).isEqualTo(UPDATED_SENS);
        assertThat(testItineraire.getDectyst()).isEqualTo(UPDATED_DECTYST);
    }

    @Test
    void fullUpdateItineraireWithPatch() throws Exception {
        // Initialize the database
        itineraireRepository.save(itineraire).block();

        int databaseSizeBeforeUpdate = itineraireRepository.findAll().collectList().block().size();

        // Update the itineraire using partial update
        Itineraire partialUpdatedItineraire = new Itineraire();
        partialUpdatedItineraire.setId(itineraire.getId());

        partialUpdatedItineraire
            .deccent(UPDATED_DECCENT)
            .decagenc(UPDATED_DECAGENC)
            .denumli(UPDATED_DENUMLI)
            .decstat(UPDATED_DECSTAT)
            .denumlg(UPDATED_DENUMLG)
            .dekmsta(UPDATED_DEKMSTA)
            .dedurtr(UPDATED_DEDURTR)
            .deescale(UPDATED_DEESCALE)
            .embra(UPDATED_EMBRA)
            .section(UPDATED_SECTION)
            .sens(UPDATED_SENS)
            .dectyst(UPDATED_DECTYST);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedItineraire.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedItineraire))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Itineraire in the database
        List<Itineraire> itineraireList = itineraireRepository.findAll().collectList().block();
        assertThat(itineraireList).hasSize(databaseSizeBeforeUpdate);
        Itineraire testItineraire = itineraireList.get(itineraireList.size() - 1);
        assertThat(testItineraire.getDeccent()).isEqualTo(UPDATED_DECCENT);
        assertThat(testItineraire.getDecagenc()).isEqualTo(UPDATED_DECAGENC);
        assertThat(testItineraire.getDenumli()).isEqualTo(UPDATED_DENUMLI);
        assertThat(testItineraire.getDecstat()).isEqualTo(UPDATED_DECSTAT);
        assertThat(testItineraire.getDenumlg()).isEqualTo(UPDATED_DENUMLG);
        assertThat(testItineraire.getDekmsta()).isEqualTo(UPDATED_DEKMSTA);
        assertThat(testItineraire.getDedurtr()).isEqualTo(UPDATED_DEDURTR);
        assertThat(testItineraire.getDeescale()).isEqualTo(UPDATED_DEESCALE);
        assertThat(testItineraire.getEmbra()).isEqualTo(UPDATED_EMBRA);
        assertThat(testItineraire.getSection()).isEqualTo(UPDATED_SECTION);
        assertThat(testItineraire.getSens()).isEqualTo(UPDATED_SENS);
        assertThat(testItineraire.getDectyst()).isEqualTo(UPDATED_DECTYST);
    }

    @Test
    void patchNonExistingItineraire() throws Exception {
        int databaseSizeBeforeUpdate = itineraireRepository.findAll().collectList().block().size();
        itineraire.setId(UUID.randomUUID().toString());

        // Create the Itineraire
        ItineraireDTO itineraireDTO = itineraireMapper.toDto(itineraire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, itineraireDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(itineraireDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Itineraire in the database
        List<Itineraire> itineraireList = itineraireRepository.findAll().collectList().block();
        assertThat(itineraireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchItineraire() throws Exception {
        int databaseSizeBeforeUpdate = itineraireRepository.findAll().collectList().block().size();
        itineraire.setId(UUID.randomUUID().toString());

        // Create the Itineraire
        ItineraireDTO itineraireDTO = itineraireMapper.toDto(itineraire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(itineraireDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Itineraire in the database
        List<Itineraire> itineraireList = itineraireRepository.findAll().collectList().block();
        assertThat(itineraireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamItineraire() throws Exception {
        int databaseSizeBeforeUpdate = itineraireRepository.findAll().collectList().block().size();
        itineraire.setId(UUID.randomUUID().toString());

        // Create the Itineraire
        ItineraireDTO itineraireDTO = itineraireMapper.toDto(itineraire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(itineraireDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Itineraire in the database
        List<Itineraire> itineraireList = itineraireRepository.findAll().collectList().block();
        assertThat(itineraireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteItineraire() {
        // Initialize the database
        itineraireRepository.save(itineraire).block();

        int databaseSizeBeforeDelete = itineraireRepository.findAll().collectList().block().size();

        // Delete the itineraire
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, itineraire.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Itineraire> itineraireList = itineraireRepository.findAll().collectList().block();
        assertThat(itineraireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
