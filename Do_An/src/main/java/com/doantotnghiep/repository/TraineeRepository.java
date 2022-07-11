package com.doantotnghiep.repository;

import com.doantotnghiep.domain.Trainee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Trainee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {}
