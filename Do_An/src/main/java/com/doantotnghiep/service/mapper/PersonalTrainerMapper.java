package com.doantotnghiep.service.mapper;

import com.doantotnghiep.domain.PersonalTrainer;
import com.doantotnghiep.service.dto.PersonalTrainerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonalTrainer} and its DTO {@link PersonalTrainerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PersonalTrainerMapper extends EntityMapper<PersonalTrainerDTO, PersonalTrainer> {}
