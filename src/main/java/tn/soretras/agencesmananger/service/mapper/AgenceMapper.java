package tn.soretras.agencesmananger.service.mapper;

import org.mapstruct.*;
import tn.soretras.agencesmananger.domain.Agence;
import tn.soretras.agencesmananger.service.dto.AgenceDTO;

/**
 * Mapper for the entity {@link Agence} and its DTO {@link AgenceDTO}.
 */
@Mapper(componentModel = "spring")
public interface AgenceMapper extends EntityMapper<AgenceDTO, Agence> {}
