package tn.soretras.itineraires.service.mapper;

import org.mapstruct.*;
import tn.soretras.itineraires.domain.Itineraire;
import tn.soretras.itineraires.service.dto.ItineraireDTO;

/**
 * Mapper for the entity {@link Itineraire} and its DTO {@link ItineraireDTO}.
 */
@Mapper(componentModel = "spring")
public interface ItineraireMapper extends EntityMapper<ItineraireDTO, Itineraire> {}
