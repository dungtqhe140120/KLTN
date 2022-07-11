package com.doantotnghiep.service.mapper;

import com.doantotnghiep.domain.Admin;
import com.doantotnghiep.service.dto.AdminDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Admin} and its DTO {@link AdminDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdminMapper extends EntityMapper<AdminDTO, Admin> {}
