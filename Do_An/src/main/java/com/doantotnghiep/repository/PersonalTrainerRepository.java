package com.doantotnghiep.repository;

import com.doantotnghiep.domain.PersonalTrainer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PersonalTrainer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonalTrainerRepository extends JpaRepository<PersonalTrainer, Long> {}
