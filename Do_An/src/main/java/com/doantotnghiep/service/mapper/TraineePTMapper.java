package com.doantotnghiep.service.mapper;

import com.doantotnghiep.domain.TraineePT;
import com.doantotnghiep.service.dto.TraineePTDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TraineePT} and its DTO {@link TraineePTDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TraineePTMapper extends EntityMapper<TraineePTDTO, TraineePT> {}
