package com.doantotnghiep.service.impl;

import com.doantotnghiep.domain.TraineeExercise;
import com.doantotnghiep.repository.TraineeExerciseRepository;
import com.doantotnghiep.service.TraineeExerciseService;
import com.doantotnghiep.service.dto.TraineeExerciseDTO;
import com.doantotnghiep.service.mapper.TraineeExerciseMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TraineeExercise}.
 */
@Service
@Transactional
public class TraineeExerciseServiceImpl implements TraineeExerciseService {

    private final Logger log = LoggerFactory.getLogger(TraineeExerciseServiceImpl.class);

    private final TraineeExerciseRepository traineeExerciseRepository;

    private final TraineeExerciseMapper traineeExerciseMapper;

    public TraineeExerciseServiceImpl(TraineeExerciseRepository traineeExerciseRepository, TraineeExerciseMapper traineeExerciseMapper) {
        this.traineeExerciseRepository = traineeExerciseRepository;
        this.traineeExerciseMapper = traineeExerciseMapper;
    }

    @Override
    public TraineeExerciseDTO save(TraineeExerciseDTO traineeExerciseDTO) {
        log.debug("Request to save TraineeExercise : {}", traineeExerciseDTO);
        TraineeExercise traineeExercise = traineeExerciseMapper.toEntity(traineeExerciseDTO);
        traineeExercise = traineeExerciseRepository.save(traineeExercise);
        return traineeExerciseMapper.toDto(traineeExercise);
    }

    @Override
    public Optional<TraineeExerciseDTO> partialUpdate(TraineeExerciseDTO traineeExerciseDTO) {
        log.debug("Request to partially update TraineeExercise : {}", traineeExerciseDTO);

        return traineeExerciseRepository
            .findById(traineeExerciseDTO.getId())
            .map(existingTraineeExercise -> {
                traineeExerciseMapper.partialUpdate(existingTraineeExercise, traineeExerciseDTO);

                return existingTraineeExercise;
            })
            .map(traineeExerciseRepository::save)
            .map(traineeExerciseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TraineeExerciseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TraineeExercises");
        return traineeExerciseRepository.findAll(pageable).map(traineeExerciseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TraineeExerciseDTO> findOne(Long id) {
        log.debug("Request to get TraineeExercise : {}", id);
        return traineeExerciseRepository.findById(id).map(traineeExerciseMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TraineeExercise : {}", id);
        traineeExerciseRepository.deleteById(id);
    }
}
