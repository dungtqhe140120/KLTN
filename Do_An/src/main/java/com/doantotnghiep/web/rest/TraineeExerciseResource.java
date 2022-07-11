package com.doantotnghiep.web.rest;

import com.doantotnghiep.repository.TraineeExerciseRepository;
import com.doantotnghiep.service.TraineeExerciseService;
import com.doantotnghiep.service.dto.TraineeExerciseDTO;
import com.doantotnghiep.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.doantotnghiep.domain.TraineeExercise}.
 */
@RestController
@RequestMapping("/api")
public class TraineeExerciseResource {

    private final Logger log = LoggerFactory.getLogger(TraineeExerciseResource.class);

    private static final String ENTITY_NAME = "traineeExercise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TraineeExerciseService traineeExerciseService;

    private final TraineeExerciseRepository traineeExerciseRepository;

    public TraineeExerciseResource(TraineeExerciseService traineeExerciseService, TraineeExerciseRepository traineeExerciseRepository) {
        this.traineeExerciseService = traineeExerciseService;
        this.traineeExerciseRepository = traineeExerciseRepository;
    }

    /**
     * {@code POST  /trainee-exercises} : Create a new traineeExercise.
     *
     * @param traineeExerciseDTO the traineeExerciseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new traineeExerciseDTO, or with status {@code 400 (Bad Request)} if the traineeExercise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trainee-exercises")
    public ResponseEntity<TraineeExerciseDTO> createTraineeExercise(@Valid @RequestBody TraineeExerciseDTO traineeExerciseDTO)
        throws URISyntaxException {
        log.debug("REST request to save TraineeExercise : {}", traineeExerciseDTO);
        if (traineeExerciseDTO.getId() != null) {
            throw new BadRequestAlertException("A new traineeExercise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TraineeExerciseDTO result = traineeExerciseService.save(traineeExerciseDTO);
        return ResponseEntity
            .created(new URI("/api/trainee-exercises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trainee-exercises/:id} : Updates an existing traineeExercise.
     *
     * @param id the id of the traineeExerciseDTO to save.
     * @param traineeExerciseDTO the traineeExerciseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated traineeExerciseDTO,
     * or with status {@code 400 (Bad Request)} if the traineeExerciseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the traineeExerciseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trainee-exercises/{id}")
    public ResponseEntity<TraineeExerciseDTO> updateTraineeExercise(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TraineeExerciseDTO traineeExerciseDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TraineeExercise : {}, {}", id, traineeExerciseDTO);
        if (traineeExerciseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, traineeExerciseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!traineeExerciseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TraineeExerciseDTO result = traineeExerciseService.save(traineeExerciseDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, traineeExerciseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /trainee-exercises/:id} : Partial updates given fields of an existing traineeExercise, field will ignore if it is null
     *
     * @param id the id of the traineeExerciseDTO to save.
     * @param traineeExerciseDTO the traineeExerciseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated traineeExerciseDTO,
     * or with status {@code 400 (Bad Request)} if the traineeExerciseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the traineeExerciseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the traineeExerciseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/trainee-exercises/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TraineeExerciseDTO> partialUpdateTraineeExercise(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TraineeExerciseDTO traineeExerciseDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TraineeExercise partially : {}, {}", id, traineeExerciseDTO);
        if (traineeExerciseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, traineeExerciseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!traineeExerciseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TraineeExerciseDTO> result = traineeExerciseService.partialUpdate(traineeExerciseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, traineeExerciseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /trainee-exercises} : get all the traineeExercises.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of traineeExercises in body.
     */
    @GetMapping("/trainee-exercises")
    public ResponseEntity<List<TraineeExerciseDTO>> getAllTraineeExercises(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of TraineeExercises");
        Page<TraineeExerciseDTO> page = traineeExerciseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trainee-exercises/:id} : get the "id" traineeExercise.
     *
     * @param id the id of the traineeExerciseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the traineeExerciseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trainee-exercises/{id}")
    public ResponseEntity<TraineeExerciseDTO> getTraineeExercise(@PathVariable Long id) {
        log.debug("REST request to get TraineeExercise : {}", id);
        Optional<TraineeExerciseDTO> traineeExerciseDTO = traineeExerciseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(traineeExerciseDTO);
    }

    /**
     * {@code DELETE  /trainee-exercises/:id} : delete the "id" traineeExercise.
     *
     * @param id the id of the traineeExerciseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trainee-exercises/{id}")
    public ResponseEntity<Void> deleteTraineeExercise(@PathVariable Long id) {
        log.debug("REST request to delete TraineeExercise : {}", id);
        traineeExerciseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
