package com.doantotnghiep.service.mapper;

import com.doantotnghiep.domain.News;
import com.doantotnghiep.service.dto.NewsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link News} and its DTO {@link NewsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NewsMapper extends EntityMapper<NewsDTO, News> {}
