package com.doantotnghiep.service.mapper;

import com.doantotnghiep.domain.Card;
import com.doantotnghiep.service.dto.CardDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Card} and its DTO {@link CardDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CardMapper extends EntityMapper<CardDTO, Card> {}
