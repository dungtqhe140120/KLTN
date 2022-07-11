package com.doantotnghiep.service.mapper;

import com.doantotnghiep.domain.Exercise;
import com.doantotnghiep.service.dto.ExerciseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Exercise} and its DTO {@link ExerciseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExerciseMapper extends EntityMapper<ExerciseDTO, Exercise> {}
