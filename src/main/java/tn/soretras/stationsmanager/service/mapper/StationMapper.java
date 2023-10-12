package tn.soretras.stationsmanager.service.mapper;

import org.mapstruct.*;
import tn.soretras.stationsmanager.domain.Station;
import tn.soretras.stationsmanager.service.dto.StationDTO;

/**
 * Mapper for the entity {@link Station} and its DTO {@link StationDTO}.
 */
@Mapper(componentModel = "spring")
public interface StationMapper extends EntityMapper<StationDTO, Station> {}
