package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CaptureDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Capture} and its DTO {@link CaptureDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CaptureMapper extends EntityMapper<CaptureDTO, Capture> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CaptureDTO toDtoId(Capture capture);
}
