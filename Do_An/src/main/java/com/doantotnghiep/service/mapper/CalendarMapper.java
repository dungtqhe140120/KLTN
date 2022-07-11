package com.doantotnghiep.service.mapper;

import com.doantotnghiep.domain.Calendar;
import com.doantotnghiep.service.dto.CalendarDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Calendar} and its DTO {@link CalendarDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CalendarMapper extends EntityMapper<CalendarDTO, Calendar> {}
