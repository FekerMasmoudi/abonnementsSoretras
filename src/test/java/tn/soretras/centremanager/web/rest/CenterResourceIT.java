package tn.soretras.centremanager.web.rest;

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
import tn.soretras.centremanager.IntegrationTest;
import tn.soretras.centremanager.domain.Center;
import tn.soretras.centremanager.repository.CenterRepository;
import tn.soretras.centremanager.service.dto.CenterDTO;
import tn.soretras.centremanager.service.mapper.CenterMapper;

/**
 * Integration tests for the {@link CenterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class CenterResourceIT {

    private static final Integer DEFAULT_DECCENT = 1;
    private static final Integer UPDATED_DECCENT = 2;

    private static final String DEFAULT_DELCENT = "AAAAAAAAAA";
    private static final String UPDATED_DELCENT = "BBBBBBBBBB";

    private static final String DEFAULT_DELCENTFR = "AAAAAAAAAA";
    private static final String UPDATED_DELCENTFR = "BBBBBBBBBB";

    private static final String DEFAULT_DEADRCE = "AAAAAAAAAA";
    private static final String UPDATED_DEADRCE = "BBBBBBBBBB";

    private static final String DEFAULT_DEOBSER = "AAAAAAAAAA";
    private static final String UPDATED_DEOBSER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/centers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private CenterMapper centerMapper;

    @Autowired
    private WebTestClient webTestClient;

    private Center center;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Center createEntity() {
        Center center = new Center()
            .deccent(DEFAULT_DECCENT)
            .delcent(DEFAULT_DELCENT)
            .delcentfr(DEFAULT_DELCENTFR)
            .deadrce(DEFAULT_DEADRCE)
            .deobser(DEFAULT_DEOBSER);
        return center;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Center createUpdatedEntity() {
        Center center = new Center()
            .deccent(UPDATED_DECCENT)
            .delcent(UPDATED_DELCENT)
            .delcentfr(UPDATED_DELCENTFR)
            .deadrce(UPDATED_DEADRCE)
            .deobser(UPDATED_DEOBSER);
        return center;
    }

    @BeforeEach
    public void initTest() {
        centerRepository.deleteAll().block();
        center = createEntity();
    }

    @Test
    void createCenter() throws Exception {
        int databaseSizeBeforeCreate = centerRepository.findAll().collectList().block().size();
        // Create the Center
        CenterDTO centerDTO = centerMapper.toDto(center);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(centerDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll().collectList().block();
        assertThat(centerList).hasSize(databaseSizeBeforeCreate + 1);
        Center testCenter = centerList.get(centerList.size() - 1);
        assertThat(testCenter.getDeccent()).isEqualTo(DEFAULT_DECCENT);
        assertThat(testCenter.getDelcent()).isEqualTo(DEFAULT_DELCENT);
        assertThat(testCenter.getDelcentfr()).isEqualTo(DEFAULT_DELCENTFR);
        assertThat(testCenter.getDeadrce()).isEqualTo(DEFAULT_DEADRCE);
        assertThat(testCenter.getDeobser()).isEqualTo(DEFAULT_DEOBSER);
    }

    @Test
    void createCenterWithExistingId() throws Exception {
        // Create the Center with an existing ID
        center.setId("existing_id");
        CenterDTO centerDTO = centerMapper.toDto(center);

        int databaseSizeBeforeCreate = centerRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(centerDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll().collectList().block();
        assertThat(centerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkDeccentIsRequired() throws Exception {
        int databaseSizeBeforeTest = centerRepository.findAll().collectList().block().size();
        // set the field null
        center.setDeccent(null);

        // Create the Center, which fails.
        CenterDTO centerDTO = centerMapper.toDto(center);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(centerDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Center> centerList = centerRepository.findAll().collectList().block();
        assertThat(centerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllCenters() {
        // Initialize the database
        centerRepository.save(center).block();

        // Get all the centerList
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
            .value(hasItem(center.getId()))
            .jsonPath("$.[*].deccent")
            .value(hasItem(DEFAULT_DECCENT))
            .jsonPath("$.[*].delcent")
            .value(hasItem(DEFAULT_DELCENT))
            .jsonPath("$.[*].delcentfr")
            .value(hasItem(DEFAULT_DELCENTFR))
            .jsonPath("$.[*].deadrce")
            .value(hasItem(DEFAULT_DEADRCE))
            .jsonPath("$.[*].deobser")
            .value(hasItem(DEFAULT_DEOBSER));
    }

    @Test
    void getCenter() {
        // Initialize the database
        centerRepository.save(center).block();

        // Get the center
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, center.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(center.getId()))
            .jsonPath("$.deccent")
            .value(is(DEFAULT_DECCENT))
            .jsonPath("$.delcent")
            .value(is(DEFAULT_DELCENT))
            .jsonPath("$.delcentfr")
            .value(is(DEFAULT_DELCENTFR))
            .jsonPath("$.deadrce")
            .value(is(DEFAULT_DEADRCE))
            .jsonPath("$.deobser")
            .value(is(DEFAULT_DEOBSER));
    }

    @Test
    void getNonExistingCenter() {
        // Get the center
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingCenter() throws Exception {
        // Initialize the database
        centerRepository.save(center).block();

        int databaseSizeBeforeUpdate = centerRepository.findAll().collectList().block().size();

        // Update the center
        Center updatedCenter = centerRepository.findById(center.getId()).block();
        updatedCenter
            .deccent(UPDATED_DECCENT)
            .delcent(UPDATED_DELCENT)
            .delcentfr(UPDATED_DELCENTFR)
            .deadrce(UPDATED_DEADRCE)
            .deobser(UPDATED_DEOBSER);
        CenterDTO centerDTO = centerMapper.toDto(updatedCenter);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, centerDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(centerDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll().collectList().block();
        assertThat(centerList).hasSize(databaseSizeBeforeUpdate);
        Center testCenter = centerList.get(centerList.size() - 1);
        assertThat(testCenter.getDeccent()).isEqualTo(UPDATED_DECCENT);
        assertThat(testCenter.getDelcent()).isEqualTo(UPDATED_DELCENT);
        assertThat(testCenter.getDelcentfr()).isEqualTo(UPDATED_DELCENTFR);
        assertThat(testCenter.getDeadrce()).isEqualTo(UPDATED_DEADRCE);
        assertThat(testCenter.getDeobser()).isEqualTo(UPDATED_DEOBSER);
    }

    @Test
    void putNonExistingCenter() throws Exception {
        int databaseSizeBeforeUpdate = centerRepository.findAll().collectList().block().size();
        center.setId(UUID.randomUUID().toString());

        // Create the Center
        CenterDTO centerDTO = centerMapper.toDto(center);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, centerDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(centerDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll().collectList().block();
        assertThat(centerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCenter() throws Exception {
        int databaseSizeBeforeUpdate = centerRepository.findAll().collectList().block().size();
        center.setId(UUID.randomUUID().toString());

        // Create the Center
        CenterDTO centerDTO = centerMapper.toDto(center);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(centerDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll().collectList().block();
        assertThat(centerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCenter() throws Exception {
        int databaseSizeBeforeUpdate = centerRepository.findAll().collectList().block().size();
        center.setId(UUID.randomUUID().toString());

        // Create the Center
        CenterDTO centerDTO = centerMapper.toDto(center);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(centerDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll().collectList().block();
        assertThat(centerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCenterWithPatch() throws Exception {
        // Initialize the database
        centerRepository.save(center).block();

        int databaseSizeBeforeUpdate = centerRepository.findAll().collectList().block().size();

        // Update the center using partial update
        Center partialUpdatedCenter = new Center();
        partialUpdatedCenter.setId(center.getId());

        partialUpdatedCenter.deccent(UPDATED_DECCENT).deadrce(UPDATED_DEADRCE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCenter.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedCenter))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll().collectList().block();
        assertThat(centerList).hasSize(databaseSizeBeforeUpdate);
        Center testCenter = centerList.get(centerList.size() - 1);
        assertThat(testCenter.getDeccent()).isEqualTo(UPDATED_DECCENT);
        assertThat(testCenter.getDelcent()).isEqualTo(DEFAULT_DELCENT);
        assertThat(testCenter.getDelcentfr()).isEqualTo(DEFAULT_DELCENTFR);
        assertThat(testCenter.getDeadrce()).isEqualTo(UPDATED_DEADRCE);
        assertThat(testCenter.getDeobser()).isEqualTo(DEFAULT_DEOBSER);
    }

    @Test
    void fullUpdateCenterWithPatch() throws Exception {
        // Initialize the database
        centerRepository.save(center).block();

        int databaseSizeBeforeUpdate = centerRepository.findAll().collectList().block().size();

        // Update the center using partial update
        Center partialUpdatedCenter = new Center();
        partialUpdatedCenter.setId(center.getId());

        partialUpdatedCenter
            .deccent(UPDATED_DECCENT)
            .delcent(UPDATED_DELCENT)
            .delcentfr(UPDATED_DELCENTFR)
            .deadrce(UPDATED_DEADRCE)
            .deobser(UPDATED_DEOBSER);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCenter.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedCenter))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll().collectList().block();
        assertThat(centerList).hasSize(databaseSizeBeforeUpdate);
        Center testCenter = centerList.get(centerList.size() - 1);
        assertThat(testCenter.getDeccent()).isEqualTo(UPDATED_DECCENT);
        assertThat(testCenter.getDelcent()).isEqualTo(UPDATED_DELCENT);
        assertThat(testCenter.getDelcentfr()).isEqualTo(UPDATED_DELCENTFR);
        assertThat(testCenter.getDeadrce()).isEqualTo(UPDATED_DEADRCE);
        assertThat(testCenter.getDeobser()).isEqualTo(UPDATED_DEOBSER);
    }

    @Test
    void patchNonExistingCenter() throws Exception {
        int databaseSizeBeforeUpdate = centerRepository.findAll().collectList().block().size();
        center.setId(UUID.randomUUID().toString());

        // Create the Center
        CenterDTO centerDTO = centerMapper.toDto(center);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, centerDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(centerDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll().collectList().block();
        assertThat(centerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCenter() throws Exception {
        int databaseSizeBeforeUpdate = centerRepository.findAll().collectList().block().size();
        center.setId(UUID.randomUUID().toString());

        // Create the Center
        CenterDTO centerDTO = centerMapper.toDto(center);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(centerDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll().collectList().block();
        assertThat(centerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCenter() throws Exception {
        int databaseSizeBeforeUpdate = centerRepository.findAll().collectList().block().size();
        center.setId(UUID.randomUUID().toString());

        // Create the Center
        CenterDTO centerDTO = centerMapper.toDto(center);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(centerDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll().collectList().block();
        assertThat(centerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCenter() {
        // Initialize the database
        centerRepository.save(center).block();

        int databaseSizeBeforeDelete = centerRepository.findAll().collectList().block().size();

        // Delete the center
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, center.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Center> centerList = centerRepository.findAll().collectList().block();
        assertThat(centerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
