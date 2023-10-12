package tn.soretras.centremanager.service.mapper;

import org.mapstruct.*;
import tn.soretras.centremanager.domain.Center;
import tn.soretras.centremanager.service.dto.CenterDTO;

/**
 * Mapper for the entity {@link Center} and its DTO {@link CenterDTO}.
 */
@Mapper(componentModel = "spring")
public interface CenterMapper extends EntityMapper<CenterDTO, Center> {}
