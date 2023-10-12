package tn.soretras.clientsmanager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import tn.soretras.clientsmanager.IntegrationTest;
import tn.soretras.clientsmanager.domain.Client;
import tn.soretras.clientsmanager.domain.enumeration.ETypeClient;
import tn.soretras.clientsmanager.repository.ClientRepository;
import tn.soretras.clientsmanager.service.dto.ClientDTO;
import tn.soretras.clientsmanager.service.mapper.ClientMapper;

/**
 * Integration tests for the {@link ClientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ClientResourceIT {

    private static final String DEFAULT_NOMPRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMPRE = "BBBBBBBBBB";

    private static final ETypeClient DEFAULT_TYPECLT = ETypeClient.ELEVE;
    private static final ETypeClient UPDATED_TYPECLT = ETypeClient.ETUDIANT;

    private static final LocalDate DEFAULT_DATENAIS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATENAIS = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CIN = "AAAAAAAAAA";
    private static final String UPDATED_CIN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATEEDIT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEEDIT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_IDENTUNIQ = "AAAAAAAAAA";
    private static final String UPDATED_IDENTUNIQ = "BBBBBBBBBB";

    private static final String DEFAULT_IDETABLISSEMENT = "AAAAAAAAAA";
    private static final String UPDATED_IDETABLISSEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSE = "AAAAAAAAAA";
    private static final String UPDATED_CLASSE = "BBBBBBBBBB";

    private static final String DEFAULT_NOMPREPAR = "AAAAAAAAAA";
    private static final String UPDATED_NOMPREPAR = "BBBBBBBBBB";

    private static final String DEFAULT_ADDR = "AAAAAAAAAA";
    private static final String UPDATED_ADDR = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_IMG = "AAAAAAAAAA";
    private static final String UPDATED_IMG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private WebTestClient webTestClient;

    private Client client;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createEntity() {
        Client client = new Client()
            .nompre(DEFAULT_NOMPRE)
            .typeclt(DEFAULT_TYPECLT)
            .datenais(DEFAULT_DATENAIS)
            .cin(DEFAULT_CIN)
            .dateedit(DEFAULT_DATEEDIT)
            .identuniq(DEFAULT_IDENTUNIQ)
            .idetablissement(DEFAULT_IDETABLISSEMENT)
            .classe(DEFAULT_CLASSE)
            .nomprepar(DEFAULT_NOMPREPAR)
            .addr(DEFAULT_ADDR)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .img(DEFAULT_IMG);
        return client;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity() {
        Client client = new Client()
            .nompre(UPDATED_NOMPRE)
            .typeclt(UPDATED_TYPECLT)
            .datenais(UPDATED_DATENAIS)
            .cin(UPDATED_CIN)
            .dateedit(UPDATED_DATEEDIT)
            .identuniq(UPDATED_IDENTUNIQ)
            .idetablissement(UPDATED_IDETABLISSEMENT)
            .classe(UPDATED_CLASSE)
            .nomprepar(UPDATED_NOMPREPAR)
            .addr(UPDATED_ADDR)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .img(UPDATED_IMG);
        return client;
    }

    @BeforeEach
    public void initTest() {
        clientRepository.deleteAll().block();
        client = createEntity();
    }

    @Test
    void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().collectList().block().size();
        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getNompre()).isEqualTo(DEFAULT_NOMPRE);
        assertThat(testClient.getTypeclt()).isEqualTo(DEFAULT_TYPECLT);
        assertThat(testClient.getDatenais()).isEqualTo(DEFAULT_DATENAIS);
        assertThat(testClient.getCin()).isEqualTo(DEFAULT_CIN);
        assertThat(testClient.getDateedit()).isEqualTo(DEFAULT_DATEEDIT);
        assertThat(testClient.getIdentuniq()).isEqualTo(DEFAULT_IDENTUNIQ);
        assertThat(testClient.getIdetablissement()).isEqualTo(DEFAULT_IDETABLISSEMENT);
        assertThat(testClient.getClasse()).isEqualTo(DEFAULT_CLASSE);
        assertThat(testClient.getNomprepar()).isEqualTo(DEFAULT_NOMPREPAR);
        assertThat(testClient.getAddr()).isEqualTo(DEFAULT_ADDR);
        assertThat(testClient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClient.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testClient.getImg()).isEqualTo(DEFAULT_IMG);
    }

    @Test
    void createClientWithExistingId() throws Exception {
        // Create the Client with an existing ID
        client.setId("existing_id");
        ClientDTO clientDTO = clientMapper.toDto(client);

        int databaseSizeBeforeCreate = clientRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNompreIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().collectList().block().size();
        // set the field null
        client.setNompre(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkTypecltIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().collectList().block().size();
        // set the field null
        client.setTypeclt(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDatenaisIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().collectList().block().size();
        // set the field null
        client.setDatenais(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCinIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().collectList().block().size();
        // set the field null
        client.setCin(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().collectList().block().size();
        // set the field null
        client.setPhone(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkImgIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().collectList().block().size();
        // set the field null
        client.setImg(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllClients() {
        // Initialize the database
        clientRepository.save(client).block();

        // Get all the clientList
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
            .value(hasItem(client.getId()))
            .jsonPath("$.[*].nompre")
            .value(hasItem(DEFAULT_NOMPRE))
            .jsonPath("$.[*].typeclt")
            .value(hasItem(DEFAULT_TYPECLT.toString()))
            .jsonPath("$.[*].datenais")
            .value(hasItem(DEFAULT_DATENAIS.toString()))
            .jsonPath("$.[*].cin")
            .value(hasItem(DEFAULT_CIN))
            .jsonPath("$.[*].dateedit")
            .value(hasItem(DEFAULT_DATEEDIT.toString()))
            .jsonPath("$.[*].identuniq")
            .value(hasItem(DEFAULT_IDENTUNIQ))
            .jsonPath("$.[*].idetablissement")
            .value(hasItem(DEFAULT_IDETABLISSEMENT))
            .jsonPath("$.[*].classe")
            .value(hasItem(DEFAULT_CLASSE))
            .jsonPath("$.[*].nomprepar")
            .value(hasItem(DEFAULT_NOMPREPAR))
            .jsonPath("$.[*].addr")
            .value(hasItem(DEFAULT_ADDR))
            .jsonPath("$.[*].email")
            .value(hasItem(DEFAULT_EMAIL))
            .jsonPath("$.[*].phone")
            .value(hasItem(DEFAULT_PHONE))
            .jsonPath("$.[*].img")
            .value(hasItem(DEFAULT_IMG));
    }

    @Test
    void getClient() {
        // Initialize the database
        clientRepository.save(client).block();

        // Get the client
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, client.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(client.getId()))
            .jsonPath("$.nompre")
            .value(is(DEFAULT_NOMPRE))
            .jsonPath("$.typeclt")
            .value(is(DEFAULT_TYPECLT.toString()))
            .jsonPath("$.datenais")
            .value(is(DEFAULT_DATENAIS.toString()))
            .jsonPath("$.cin")
            .value(is(DEFAULT_CIN))
            .jsonPath("$.dateedit")
            .value(is(DEFAULT_DATEEDIT.toString()))
            .jsonPath("$.identuniq")
            .value(is(DEFAULT_IDENTUNIQ))
            .jsonPath("$.idetablissement")
            .value(is(DEFAULT_IDETABLISSEMENT))
            .jsonPath("$.classe")
            .value(is(DEFAULT_CLASSE))
            .jsonPath("$.nomprepar")
            .value(is(DEFAULT_NOMPREPAR))
            .jsonPath("$.addr")
            .value(is(DEFAULT_ADDR))
            .jsonPath("$.email")
            .value(is(DEFAULT_EMAIL))
            .jsonPath("$.phone")
            .value(is(DEFAULT_PHONE))
            .jsonPath("$.img")
            .value(is(DEFAULT_IMG));
    }

    @Test
    void getNonExistingClient() {
        // Get the client
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingClient() throws Exception {
        // Initialize the database
        clientRepository.save(client).block();

        int databaseSizeBeforeUpdate = clientRepository.findAll().collectList().block().size();

        // Update the client
        Client updatedClient = clientRepository.findById(client.getId()).block();
        updatedClient
            .nompre(UPDATED_NOMPRE)
            .typeclt(UPDATED_TYPECLT)
            .datenais(UPDATED_DATENAIS)
            .cin(UPDATED_CIN)
            .dateedit(UPDATED_DATEEDIT)
            .identuniq(UPDATED_IDENTUNIQ)
            .idetablissement(UPDATED_IDETABLISSEMENT)
            .classe(UPDATED_CLASSE)
            .nomprepar(UPDATED_NOMPREPAR)
            .addr(UPDATED_ADDR)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .img(UPDATED_IMG);
        ClientDTO clientDTO = clientMapper.toDto(updatedClient);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, clientDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getNompre()).isEqualTo(UPDATED_NOMPRE);
        assertThat(testClient.getTypeclt()).isEqualTo(UPDATED_TYPECLT);
        assertThat(testClient.getDatenais()).isEqualTo(UPDATED_DATENAIS);
        assertThat(testClient.getCin()).isEqualTo(UPDATED_CIN);
        assertThat(testClient.getDateedit()).isEqualTo(UPDATED_DATEEDIT);
        assertThat(testClient.getIdentuniq()).isEqualTo(UPDATED_IDENTUNIQ);
        assertThat(testClient.getIdetablissement()).isEqualTo(UPDATED_IDETABLISSEMENT);
        assertThat(testClient.getClasse()).isEqualTo(UPDATED_CLASSE);
        assertThat(testClient.getNomprepar()).isEqualTo(UPDATED_NOMPREPAR);
        assertThat(testClient.getAddr()).isEqualTo(UPDATED_ADDR);
        assertThat(testClient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClient.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testClient.getImg()).isEqualTo(UPDATED_IMG);
    }

    @Test
    void putNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().collectList().block().size();
        client.setId(UUID.randomUUID().toString());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, clientDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().collectList().block().size();
        client.setId(UUID.randomUUID().toString());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().collectList().block().size();
        client.setId(UUID.randomUUID().toString());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateClientWithPatch() throws Exception {
        // Initialize the database
        clientRepository.save(client).block();

        int databaseSizeBeforeUpdate = clientRepository.findAll().collectList().block().size();

        // Update the client using partial update
        Client partialUpdatedClient = new Client();
        partialUpdatedClient.setId(client.getId());

        partialUpdatedClient
            .nompre(UPDATED_NOMPRE)
            .typeclt(UPDATED_TYPECLT)
            .datenais(UPDATED_DATENAIS)
            .idetablissement(UPDATED_IDETABLISSEMENT)
            .nomprepar(UPDATED_NOMPREPAR)
            .addr(UPDATED_ADDR)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .img(UPDATED_IMG);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedClient.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedClient))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getNompre()).isEqualTo(UPDATED_NOMPRE);
        assertThat(testClient.getTypeclt()).isEqualTo(UPDATED_TYPECLT);
        assertThat(testClient.getDatenais()).isEqualTo(UPDATED_DATENAIS);
        assertThat(testClient.getCin()).isEqualTo(DEFAULT_CIN);
        assertThat(testClient.getDateedit()).isEqualTo(DEFAULT_DATEEDIT);
        assertThat(testClient.getIdentuniq()).isEqualTo(DEFAULT_IDENTUNIQ);
        assertThat(testClient.getIdetablissement()).isEqualTo(UPDATED_IDETABLISSEMENT);
        assertThat(testClient.getClasse()).isEqualTo(DEFAULT_CLASSE);
        assertThat(testClient.getNomprepar()).isEqualTo(UPDATED_NOMPREPAR);
        assertThat(testClient.getAddr()).isEqualTo(UPDATED_ADDR);
        assertThat(testClient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClient.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testClient.getImg()).isEqualTo(UPDATED_IMG);
    }

    @Test
    void fullUpdateClientWithPatch() throws Exception {
        // Initialize the database
        clientRepository.save(client).block();

        int databaseSizeBeforeUpdate = clientRepository.findAll().collectList().block().size();

        // Update the client using partial update
        Client partialUpdatedClient = new Client();
        partialUpdatedClient.setId(client.getId());

        partialUpdatedClient
            .nompre(UPDATED_NOMPRE)
            .typeclt(UPDATED_TYPECLT)
            .datenais(UPDATED_DATENAIS)
            .cin(UPDATED_CIN)
            .dateedit(UPDATED_DATEEDIT)
            .identuniq(UPDATED_IDENTUNIQ)
            .idetablissement(UPDATED_IDETABLISSEMENT)
            .classe(UPDATED_CLASSE)
            .nomprepar(UPDATED_NOMPREPAR)
            .addr(UPDATED_ADDR)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .img(UPDATED_IMG);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedClient.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedClient))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getNompre()).isEqualTo(UPDATED_NOMPRE);
        assertThat(testClient.getTypeclt()).isEqualTo(UPDATED_TYPECLT);
        assertThat(testClient.getDatenais()).isEqualTo(UPDATED_DATENAIS);
        assertThat(testClient.getCin()).isEqualTo(UPDATED_CIN);
        assertThat(testClient.getDateedit()).isEqualTo(UPDATED_DATEEDIT);
        assertThat(testClient.getIdentuniq()).isEqualTo(UPDATED_IDENTUNIQ);
        assertThat(testClient.getIdetablissement()).isEqualTo(UPDATED_IDETABLISSEMENT);
        assertThat(testClient.getClasse()).isEqualTo(UPDATED_CLASSE);
        assertThat(testClient.getNomprepar()).isEqualTo(UPDATED_NOMPREPAR);
        assertThat(testClient.getAddr()).isEqualTo(UPDATED_ADDR);
        assertThat(testClient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClient.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testClient.getImg()).isEqualTo(UPDATED_IMG);
    }

    @Test
    void patchNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().collectList().block().size();
        client.setId(UUID.randomUUID().toString());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, clientDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().collectList().block().size();
        client.setId(UUID.randomUUID().toString());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().collectList().block().size();
        client.setId(UUID.randomUUID().toString());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteClient() {
        // Initialize the database
        clientRepository.save(client).block();

        int databaseSizeBeforeDelete = clientRepository.findAll().collectList().block().size();

        // Delete the client
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, client.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
