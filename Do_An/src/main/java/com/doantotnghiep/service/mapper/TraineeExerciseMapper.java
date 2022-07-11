package com.doantotnghiep.service.mapper;

import com.doantotnghiep.domain.TraineeExercise;
import com.doantotnghiep.service.dto.TraineeExerciseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TraineeExercise} and its DTO {@link TraineeExerciseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TraineeExerciseMapper extends EntityMapper<TraineeExerciseDTO, TraineeExercise> {}
