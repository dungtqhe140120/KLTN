package com.doantotnghiep.service;

import com.doantotnghiep.service.dto.CardDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.doantotnghiep.domain.Card}.
 */
public interface CardService {
    /**
     * Save a card.
     *
     * @param cardDTO the entity to save.
     * @return the persisted entity.
     */
    CardDTO save(CardDTO cardDTO);

    /**
     * Partially updates a card.
     *
     * @param cardDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CardDTO> partialUpdate(CardDTO cardDTO);

    /**
     * Get all the cards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CardDTO> findAll(Pageable pageable);

    /**
     * Get the "id" card.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CardDTO> findOne(Long id);

    /**
     * Delete the "id" card.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
