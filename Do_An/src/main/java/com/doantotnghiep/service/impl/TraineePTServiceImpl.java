package com.doantotnghiep.service.impl;

import com.doantotnghiep.domain.TraineePT;
import com.doantotnghiep.repository.TraineePTRepository;
import com.doantotnghiep.service.TraineePTService;
import com.doantotnghiep.service.dto.TraineePTDTO;
import com.doantotnghiep.service.mapper.TraineePTMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TraineePT}.
 */
@Service
@Transactional
public class TraineePTServiceImpl implements TraineePTService {

    private final Logger log = LoggerFactory.getLogger(TraineePTServiceImpl.class);

    private final TraineePTRepository traineePTRepository;

    private final TraineePTMapper traineePTMapper;

    public TraineePTServiceImpl(TraineePTRepository traineePTRepository, TraineePTMapper traineePTMapper) {
        this.traineePTRepository = traineePTRepository;
        this.traineePTMapper = traineePTMapper;
    }

    @Override
    public TraineePTDTO save(TraineePTDTO traineePTDTO) {
        log.debug("Request to save TraineePT : {}", traineePTDTO);
        TraineePT traineePT = traineePTMapper.toEntity(traineePTDTO);
        traineePT = traineePTRepository.save(traineePT);
        return traineePTMapper.toDto(traineePT);
    }

    @Override
    public Optional<TraineePTDTO> partialUpdate(TraineePTDTO traineePTDTO) {
        log.debug("Request to partially update TraineePT : {}", traineePTDTO);

        return traineePTRepository
            .findById(traineePTDTO.getId())
            .map(existingTraineePT -> {
                traineePTMapper.partialUpdate(existingTraineePT, traineePTDTO);

                return existingTraineePT;
            })
            .map(traineePTRepository::save)
            .map(traineePTMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TraineePTDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TraineePTS");
        return traineePTRepository.findAll(pageable).map(traineePTMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TraineePTDTO> findOne(Long id) {
        log.debug("Request to get TraineePT : {}", id);
        return traineePTRepository.findById(id).map(traineePTMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TraineePT : {}", id);
        traineePTRepository.deleteById(id);
    }
}
