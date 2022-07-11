package com.doantotnghiep.repository;

import com.doantotnghiep.domain.TraineePT;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TraineePT entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TraineePTRepository extends JpaRepository<TraineePT, Long> {}
