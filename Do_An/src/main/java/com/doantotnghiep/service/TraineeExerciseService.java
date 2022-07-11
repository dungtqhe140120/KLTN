package com.doantotnghiep.service;

import com.doantotnghiep.service.dto.TraineeExerciseDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.doantotnghiep.domain.TraineeExercise}.
 */
public interface TraineeExerciseService {
    /**
     * Save a traineeExercise.
     *
     * @param traineeExerciseDTO the entity to save.
     * @return the persisted entity.
     */
    TraineeExerciseDTO save(TraineeExerciseDTO traineeExerciseDTO);

    /**
     * Partially updates a traineeExercise.
     *
     * @param traineeExerciseDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TraineeExerciseDTO> partialUpdate(TraineeExerciseDTO traineeExerciseDTO);

    /**
     * Get all the traineeExercises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TraineeExerciseDTO> findAll(Pageable pageable);

    /**
     * Get the "id" traineeExercise.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TraineeExerciseDTO> findOne(Long id);

    /**
     * Delete the "id" traineeExercise.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
