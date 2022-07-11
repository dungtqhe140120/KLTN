package com.doantotnghiep.repository;

import com.doantotnghiep.domain.TraineeExercise;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TraineeExercise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TraineeExerciseRepository extends JpaRepository<TraineeExercise, Long> {}
