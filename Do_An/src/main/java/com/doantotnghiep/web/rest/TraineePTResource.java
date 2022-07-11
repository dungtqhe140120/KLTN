package com.doantotnghiep.web.rest;

import com.doantotnghiep.repository.TraineePTRepository;
import com.doantotnghiep.service.TraineePTService;
import com.doantotnghiep.service.dto.TraineePTDTO;
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
 * REST controller for managing {@link com.doantotnghiep.domain.TraineePT}.
 */
@RestController
@RequestMapping("/api")
public class TraineePTResource {

    private final Logger log = LoggerFactory.getLogger(TraineePTResource.class);

    private static final String ENTITY_NAME = "traineePT";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TraineePTService traineePTService;

    private final TraineePTRepository traineePTRepository;

    public TraineePTResource(TraineePTService traineePTService, TraineePTRepository traineePTRepository) {
        this.traineePTService = traineePTService;
        this.traineePTRepository = traineePTRepository;
    }

    /**
     * {@code POST  /trainee-pts} : Create a new traineePT.
     *
     * @param traineePTDTO the traineePTDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new traineePTDTO, or with status {@code 400 (Bad Request)} if the traineePT has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trainee-pts")
    public ResponseEntity<TraineePTDTO> createTraineePT(@Valid @RequestBody TraineePTDTO traineePTDTO) throws URISyntaxException {
        log.debug("REST request to save TraineePT : {}", traineePTDTO);
        if (traineePTDTO.getId() != null) {
            throw new BadRequestAlertException("A new traineePT cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TraineePTDTO result = traineePTService.save(traineePTDTO);
        return ResponseEntity
            .created(new URI("/api/trainee-pts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trainee-pts/:id} : Updates an existing traineePT.
     *
     * @param id the id of the traineePTDTO to save.
     * @param traineePTDTO the traineePTDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated traineePTDTO,
     * or with status {@code 400 (Bad Request)} if the traineePTDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the traineePTDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trainee-pts/{id}")
    public ResponseEntity<TraineePTDTO> updateTraineePT(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TraineePTDTO traineePTDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TraineePT : {}, {}", id, traineePTDTO);
        if (traineePTDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, traineePTDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!traineePTRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TraineePTDTO result = traineePTService.save(traineePTDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, traineePTDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /trainee-pts/:id} : Partial updates given fields of an existing traineePT, field will ignore if it is null
     *
     * @param id the id of the traineePTDTO to save.
     * @param traineePTDTO the traineePTDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated traineePTDTO,
     * or with status {@code 400 (Bad Request)} if the traineePTDTO is not valid,
     * or with status {@code 404 (Not Found)} if the traineePTDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the traineePTDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/trainee-pts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TraineePTDTO> partialUpdateTraineePT(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TraineePTDTO traineePTDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TraineePT partially : {}, {}", id, traineePTDTO);
        if (traineePTDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, traineePTDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!traineePTRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TraineePTDTO> result = traineePTService.partialUpdate(traineePTDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, traineePTDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /trainee-pts} : get all the traineePTS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of traineePTS in body.
     */
    @GetMapping("/trainee-pts")
    public ResponseEntity<List<TraineePTDTO>> getAllTraineePTS(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TraineePTS");
        Page<TraineePTDTO> page = traineePTService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trainee-pts/:id} : get the "id" traineePT.
     *
     * @param id the id of the traineePTDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the traineePTDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trainee-pts/{id}")
    public ResponseEntity<TraineePTDTO> getTraineePT(@PathVariable Long id) {
        log.debug("REST request to get TraineePT : {}", id);
        Optional<TraineePTDTO> traineePTDTO = traineePTService.findOne(id);
        return ResponseUtil.wrapOrNotFound(traineePTDTO);
    }

    /**
     * {@code DELETE  /trainee-pts/:id} : delete the "id" traineePT.
     *
     * @param id the id of the traineePTDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trainee-pts/{id}")
    public ResponseEntity<Void> deleteTraineePT(@PathVariable Long id) {
        log.debug("REST request to delete TraineePT : {}", id);
        traineePTService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
