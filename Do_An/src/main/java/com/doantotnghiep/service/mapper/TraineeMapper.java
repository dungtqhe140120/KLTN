package com.doantotnghiep.service.mapper;

import com.doantotnghiep.domain.Trainee;
import com.doantotnghiep.service.dto.TraineeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Trainee} and its DTO {@link TraineeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TraineeMapper extends EntityMapper<TraineeDTO, Trainee> {}
