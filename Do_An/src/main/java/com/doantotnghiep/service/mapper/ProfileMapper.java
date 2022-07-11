package com.doantotnghiep.service.mapper;

import com.doantotnghiep.domain.Profile;
import com.doantotnghiep.service.dto.ProfileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Profile} and its DTO {@link ProfileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProfileMapper extends EntityMapper<ProfileDTO, Profile> {}
