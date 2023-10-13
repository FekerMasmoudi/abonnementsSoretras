package tn.soretras.agencesmananger.web.rest;

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
import tn.soretras.agencesmananger.IntegrationTest;
import tn.soretras.agencesmananger.domain.Agence;
import tn.soretras.agencesmananger.repository.AgenceRepository;
import tn.soretras.agencesmananger.service.dto.AgenceDTO;
import tn.soretras.agencesmananger.service.mapper.AgenceMapper;

/**
 * Integration tests for the {@link AgenceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class AgenceResourceIT {

    private static final Integer DEFAULT_DECCENT = 1;
    private static final Integer UPDATED_DECCENT = 2;

    private static final Integer DEFAULT_DECAGENC = 1;
    private static final Integer UPDATED_DECAGENC = 2;

    private static final String DEFAULT_DELAGENC = "AAAAAAAAAA";
    private static final String UPDATED_DELAGENC = "BBBBBBBBBB";

    private static final String DEFAULT_DELAGENCFR = "AAAAAAAAAA";
    private static final String UPDATED_DELAGENCFR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/agences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private AgenceRepository agenceRepository;

    @Autowired
    private AgenceMapper agenceMapper;

    @Autowired
    private WebTestClient webTestClient;

    private Agence agence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agence createEntity() {
        Agence agence = new Agence()
            .deccent(DEFAULT_DECCENT)
            .decagenc(DEFAULT_DECAGENC)
            .delagenc(DEFAULT_DELAGENC)
            .delagencfr(DEFAULT_DELAGENCFR);
        return agence;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agence createUpdatedEntity() {
        Agence agence = new Agence()
            .deccent(UPDATED_DECCENT)
            .decagenc(UPDATED_DECAGENC)
            .delagenc(UPDATED_DELAGENC)
            .delagencfr(UPDATED_DELAGENCFR);
        return agence;
    }

    @BeforeEach
    public void initTest() {
        agenceRepository.deleteAll().block();
        agence = createEntity();
    }

    @Test
    void createAgence() throws Exception {
        int databaseSizeBeforeCreate = agenceRepository.findAll().collectList().block().size();
        // Create the Agence
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(agenceDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll().collectList().block();
        assertThat(agenceList).hasSize(databaseSizeBeforeCreate + 1);
        Agence testAgence = agenceList.get(agenceList.size() - 1);
        assertThat(testAgence.getDeccent()).isEqualTo(DEFAULT_DECCENT);
        assertThat(testAgence.getDecagenc()).isEqualTo(DEFAULT_DECAGENC);
        assertThat(testAgence.getDelagenc()).isEqualTo(DEFAULT_DELAGENC);
        assertThat(testAgence.getDelagencfr()).isEqualTo(DEFAULT_DELAGENCFR);
    }

    @Test
    void createAgenceWithExistingId() throws Exception {
        // Create the Agence with an existing ID
        agence.setId("existing_id");
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        int databaseSizeBeforeCreate = agenceRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(agenceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll().collectList().block();
        assertThat(agenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkDeccentIsRequired() throws Exception {
        int databaseSizeBeforeTest = agenceRepository.findAll().collectList().block().size();
        // set the field null
        agence.setDeccent(null);

        // Create the Agence, which fails.
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(agenceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Agence> agenceList = agenceRepository.findAll().collectList().block();
        assertThat(agenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDecagencIsRequired() throws Exception {
        int databaseSizeBeforeTest = agenceRepository.findAll().collectList().block().size();
        // set the field null
        agence.setDecagenc(null);

        // Create the Agence, which fails.
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(agenceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Agence> agenceList = agenceRepository.findAll().collectList().block();
        assertThat(agenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllAgences() {
        // Initialize the database
        agenceRepository.save(agence).block();

        // Get all the agenceList
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
            .value(hasItem(agence.getId()))
            .jsonPath("$.[*].deccent")
            .value(hasItem(DEFAULT_DECCENT))
            .jsonPath("$.[*].decagenc")
            .value(hasItem(DEFAULT_DECAGENC))
            .jsonPath("$.[*].delagenc")
            .value(hasItem(DEFAULT_DELAGENC))
            .jsonPath("$.[*].delagencfr")
            .value(hasItem(DEFAULT_DELAGENCFR));
    }

    @Test
    void getAgence() {
        // Initialize the database
        agenceRepository.save(agence).block();

        // Get the agence
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, agence.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(agence.getId()))
            .jsonPath("$.deccent")
            .value(is(DEFAULT_DECCENT))
            .jsonPath("$.decagenc")
            .value(is(DEFAULT_DECAGENC))
            .jsonPath("$.delagenc")
            .value(is(DEFAULT_DELAGENC))
            .jsonPath("$.delagencfr")
            .value(is(DEFAULT_DELAGENCFR));
    }

    @Test
    void getNonExistingAgence() {
        // Get the agence
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingAgence() throws Exception {
        // Initialize the database
        agenceRepository.save(agence).block();

        int databaseSizeBeforeUpdate = agenceRepository.findAll().collectList().block().size();

        // Update the agence
        Agence updatedAgence = agenceRepository.findById(agence.getId()).block();
        updatedAgence.deccent(UPDATED_DECCENT).decagenc(UPDATED_DECAGENC).delagenc(UPDATED_DELAGENC).delagencfr(UPDATED_DELAGENCFR);
        AgenceDTO agenceDTO = agenceMapper.toDto(updatedAgence);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, agenceDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(agenceDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll().collectList().block();
        assertThat(agenceList).hasSize(databaseSizeBeforeUpdate);
        Agence testAgence = agenceList.get(agenceList.size() - 1);
        assertThat(testAgence.getDeccent()).isEqualTo(UPDATED_DECCENT);
        assertThat(testAgence.getDecagenc()).isEqualTo(UPDATED_DECAGENC);
        assertThat(testAgence.getDelagenc()).isEqualTo(UPDATED_DELAGENC);
        assertThat(testAgence.getDelagencfr()).isEqualTo(UPDATED_DELAGENCFR);
    }

    @Test
    void putNonExistingAgence() throws Exception {
        int databaseSizeBeforeUpdate = agenceRepository.findAll().collectList().block().size();
        agence.setId(UUID.randomUUID().toString());

        // Create the Agence
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, agenceDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(agenceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll().collectList().block();
        assertThat(agenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAgence() throws Exception {
        int databaseSizeBeforeUpdate = agenceRepository.findAll().collectList().block().size();
        agence.setId(UUID.randomUUID().toString());

        // Create the Agence
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(agenceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll().collectList().block();
        assertThat(agenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAgence() throws Exception {
        int databaseSizeBeforeUpdate = agenceRepository.findAll().collectList().block().size();
        agence.setId(UUID.randomUUID().toString());

        // Create the Agence
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(agenceDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll().collectList().block();
        assertThat(agenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAgenceWithPatch() throws Exception {
        // Initialize the database
        agenceRepository.save(agence).block();

        int databaseSizeBeforeUpdate = agenceRepository.findAll().collectList().block().size();

        // Update the agence using partial update
        Agence partialUpdatedAgence = new Agence();
        partialUpdatedAgence.setId(agence.getId());

        partialUpdatedAgence.delagenc(UPDATED_DELAGENC).delagencfr(UPDATED_DELAGENCFR);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedAgence.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedAgence))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll().collectList().block();
        assertThat(agenceList).hasSize(databaseSizeBeforeUpdate);
        Agence testAgence = agenceList.get(agenceList.size() - 1);
        assertThat(testAgence.getDeccent()).isEqualTo(DEFAULT_DECCENT);
        assertThat(testAgence.getDecagenc()).isEqualTo(DEFAULT_DECAGENC);
        assertThat(testAgence.getDelagenc()).isEqualTo(UPDATED_DELAGENC);
        assertThat(testAgence.getDelagencfr()).isEqualTo(UPDATED_DELAGENCFR);
    }

    @Test
    void fullUpdateAgenceWithPatch() throws Exception {
        // Initialize the database
        agenceRepository.save(agence).block();

        int databaseSizeBeforeUpdate = agenceRepository.findAll().collectList().block().size();

        // Update the agence using partial update
        Agence partialUpdatedAgence = new Agence();
        partialUpdatedAgence.setId(agence.getId());

        partialUpdatedAgence.deccent(UPDATED_DECCENT).decagenc(UPDATED_DECAGENC).delagenc(UPDATED_DELAGENC).delagencfr(UPDATED_DELAGENCFR);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedAgence.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedAgence))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll().collectList().block();
        assertThat(agenceList).hasSize(databaseSizeBeforeUpdate);
        Agence testAgence = agenceList.get(agenceList.size() - 1);
        assertThat(testAgence.getDeccent()).isEqualTo(UPDATED_DECCENT);
        assertThat(testAgence.getDecagenc()).isEqualTo(UPDATED_DECAGENC);
        assertThat(testAgence.getDelagenc()).isEqualTo(UPDATED_DELAGENC);
        assertThat(testAgence.getDelagencfr()).isEqualTo(UPDATED_DELAGENCFR);
    }

    @Test
    void patchNonExistingAgence() throws Exception {
        int databaseSizeBeforeUpdate = agenceRepository.findAll().collectList().block().size();
        agence.setId(UUID.randomUUID().toString());

        // Create the Agence
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, agenceDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(agenceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll().collectList().block();
        assertThat(agenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAgence() throws Exception {
        int databaseSizeBeforeUpdate = agenceRepository.findAll().collectList().block().size();
        agence.setId(UUID.randomUUID().toString());

        // Create the Agence
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(agenceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll().collectList().block();
        assertThat(agenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAgence() throws Exception {
        int databaseSizeBeforeUpdate = agenceRepository.findAll().collectList().block().size();
        agence.setId(UUID.randomUUID().toString());

        // Create the Agence
        AgenceDTO agenceDTO = agenceMapper.toDto(agence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(agenceDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll().collectList().block();
        assertThat(agenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAgence() {
        // Initialize the database
        agenceRepository.save(agence).block();

        int databaseSizeBeforeDelete = agenceRepository.findAll().collectList().block().size();

        // Delete the agence
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, agence.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Agence> agenceList = agenceRepository.findAll().collectList().block();
        assertThat(agenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
