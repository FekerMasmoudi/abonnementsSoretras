package tn.soretras.clientsmanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tn.soretras.clientsmanager.domain.Client;
import tn.soretras.clientsmanager.repository.ClientRepository;
import tn.soretras.clientsmanager.service.dto.ClientDTO;
import tn.soretras.clientsmanager.service.mapper.ClientMapper;

/**
 * Service Implementation for managing {@link Client}.
 */
@Service
public class ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    /**
     * Save a client.
     *
     * @param clientDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ClientDTO> save(ClientDTO clientDTO) {
        log.debug("Request to save Client : {}", clientDTO);
        return clientRepository.save(clientMapper.toEntity(clientDTO)).map(clientMapper::toDto);
    }

    /**
     * Update a client.
     *
     * @param clientDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ClientDTO> update(ClientDTO clientDTO) {
        log.debug("Request to update Client : {}", clientDTO);
        return clientRepository.save(clientMapper.toEntity(clientDTO)).map(clientMapper::toDto);
    }

    /**
     * Partially update a client.
     *
     * @param clientDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<ClientDTO> partialUpdate(ClientDTO clientDTO) {
        log.debug("Request to partially update Client : {}", clientDTO);

        return clientRepository
            .findById(clientDTO.getId())
            .map(existingClient -> {
                clientMapper.partialUpdate(existingClient, clientDTO);

                return existingClient;
            })
            .flatMap(clientRepository::save)
            .map(clientMapper::toDto);
    }

    /**
     * Get all the clients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Flux<ClientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        return clientRepository.findAllBy(pageable).map(clientMapper::toDto);
    }

    /**
     * Returns the number of clients available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return clientRepository.count();
    }

    /**
     * Get one client by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Mono<ClientDTO> findOne(String id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id).map(clientMapper::toDto);
    }

    /**
     * Delete the client by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Client : {}", id);
        return clientRepository.deleteById(id);
    }
}
