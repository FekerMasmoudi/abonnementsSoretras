package tn.soretras.clientsmanager.service.mapper;

import org.mapstruct.*;
import tn.soretras.clientsmanager.domain.Client;
import tn.soretras.clientsmanager.service.dto.ClientDTO;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {}
