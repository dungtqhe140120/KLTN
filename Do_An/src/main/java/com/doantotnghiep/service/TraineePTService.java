package com.doantotnghiep.service;

import com.doantotnghiep.service.dto.TraineePTDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.doantotnghiep.domain.TraineePT}.
 */
public interface TraineePTService {
    /**
     * Save a traineePT.
     *
     * @param traineePTDTO the entity to save.
     * @return the persisted entity.
     */
    TraineePTDTO save(TraineePTDTO traineePTDTO);

    /**
     * Partially updates a traineePT.
     *
     * @param traineePTDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TraineePTDTO> partialUpdate(TraineePTDTO traineePTDTO);

    /**
     * Get all the traineePTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TraineePTDTO> findAll(Pageable pageable);

    /**
     * Get the "id" traineePT.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TraineePTDTO> findOne(Long id);

    /**
     * Delete the "id" traineePT.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
