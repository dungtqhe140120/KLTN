package com.doantotnghiep.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.doantotnghiep.IntegrationTest;
import com.doantotnghiep.domain.TraineeExercise;
import com.doantotnghiep.repository.TraineeExerciseRepository;
import com.doantotnghiep.service.dto.TraineeExerciseDTO;
import com.doantotnghiep.service.mapper.TraineeExerciseMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TraineeExerciseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TraineeExerciseResourceIT {

    private static final Long DEFAULT_TRAINEE_ID = 1L;
    private static final Long UPDATED_TRAINEE_ID = 2L;

    private static final Long DEFAULT_EXERCISE_ID = 1L;
    private static final Long UPDATED_EXERCISE_ID = 2L;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_MODIFIED_BY = 1L;
    private static final Long UPDATED_MODIFIED_BY = 2L;

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/trainee-exercises";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TraineeExerciseRepository traineeExerciseRepository;

    @Autowired
    private TraineeExerciseMapper traineeExerciseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTraineeExerciseMockMvc;

    private TraineeExercise traineeExercise;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TraineeExercise createEntity(EntityManager em) {
        TraineeExercise traineeExercise = new TraineeExercise()
            .trainee_id(DEFAULT_TRAINEE_ID)
            .exercise_id(DEFAULT_EXERCISE_ID)
            .note(DEFAULT_NOTE)
            .created_by(DEFAULT_CREATED_BY)
            .created_Date(DEFAULT_CREATED_DATE)
            .modified_by(DEFAULT_MODIFIED_BY)
            .modified_date(DEFAULT_MODIFIED_DATE);
        return traineeExercise;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TraineeExercise createUpdatedEntity(EntityManager em) {
        TraineeExercise traineeExercise = new TraineeExercise()
            .trainee_id(UPDATED_TRAINEE_ID)
            .exercise_id(UPDATED_EXERCISE_ID)
            .note(UPDATED_NOTE)
            .created_by(UPDATED_CREATED_BY)
            .created_Date(UPDATED_CREATED_DATE)
            .modified_by(UPDATED_MODIFIED_BY)
            .modified_date(UPDATED_MODIFIED_DATE);
        return traineeExercise;
    }

    @BeforeEach
    public void initTest() {
        traineeExercise = createEntity(em);
    }

    @Test
    @Transactional
    void createTraineeExercise() throws Exception {
        int databaseSizeBeforeCreate = traineeExerciseRepository.findAll().size();
        // Create the TraineeExercise
        TraineeExerciseDTO traineeExerciseDTO = traineeExerciseMapper.toDto(traineeExercise);
        restTraineeExerciseMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traineeExerciseDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TraineeExercise in the database
        List<TraineeExercise> traineeExerciseList = traineeExerciseRepository.findAll();
        assertThat(traineeExerciseList).hasSize(databaseSizeBeforeCreate + 1);
        TraineeExercise testTraineeExercise = traineeExerciseList.get(traineeExerciseList.size() - 1);
        assertThat(testTraineeExercise.getTrainee_id()).isEqualTo(DEFAULT_TRAINEE_ID);
        assertThat(testTraineeExercise.getExercise_id()).isEqualTo(DEFAULT_EXERCISE_ID);
        assertThat(testTraineeExercise.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testTraineeExercise.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTraineeExercise.getCreated_Date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTraineeExercise.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testTraineeExercise.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createTraineeExerciseWithExistingId() throws Exception {
        // Create the TraineeExercise with an existing ID
        traineeExercise.setId(1L);
        TraineeExerciseDTO traineeExerciseDTO = traineeExerciseMapper.toDto(traineeExercise);

        int databaseSizeBeforeCreate = traineeExerciseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraineeExerciseMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traineeExerciseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TraineeExercise in the database
        List<TraineeExercise> traineeExerciseList = traineeExerciseRepository.findAll();
        assertThat(traineeExerciseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTrainee_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = traineeExerciseRepository.findAll().size();
        // set the field null
        traineeExercise.setTrainee_id(null);

        // Create the TraineeExercise, which fails.
        TraineeExerciseDTO traineeExerciseDTO = traineeExerciseMapper.toDto(traineeExercise);

        restTraineeExerciseMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traineeExerciseDTO))
            )
            .andExpect(status().isBadRequest());

        List<TraineeExercise> traineeExerciseList = traineeExerciseRepository.findAll();
        assertThat(traineeExerciseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExercise_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = traineeExerciseRepository.findAll().size();
        // set the field null
        traineeExercise.setExercise_id(null);

        // Create the TraineeExercise, which fails.
        TraineeExerciseDTO traineeExerciseDTO = traineeExerciseMapper.toDto(traineeExercise);

        restTraineeExerciseMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traineeExerciseDTO))
            )
            .andExpect(status().isBadRequest());

        List<TraineeExercise> traineeExerciseList = traineeExerciseRepository.findAll();
        assertThat(traineeExerciseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = traineeExerciseRepository.findAll().size();
        // set the field null
        traineeExercise.setCreated_by(null);

        // Create the TraineeExercise, which fails.
        TraineeExerciseDTO traineeExerciseDTO = traineeExerciseMapper.toDto(traineeExercise);

        restTraineeExerciseMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traineeExerciseDTO))
            )
            .andExpect(status().isBadRequest());

        List<TraineeExercise> traineeExerciseList = traineeExerciseRepository.findAll();
        assertThat(traineeExerciseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = traineeExerciseRepository.findAll().size();
        // set the field null
        traineeExercise.setModified_by(null);

        // Create the TraineeExercise, which fails.
        TraineeExerciseDTO traineeExerciseDTO = traineeExerciseMapper.toDto(traineeExercise);

        restTraineeExerciseMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traineeExerciseDTO))
            )
            .andExpect(status().isBadRequest());

        List<TraineeExercise> traineeExerciseList = traineeExerciseRepository.findAll();
        assertThat(traineeExerciseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = traineeExerciseRepository.findAll().size();
        // set the field null
        traineeExercise.setModified_date(null);

        // Create the TraineeExercise, which fails.
        TraineeExerciseDTO traineeExerciseDTO = traineeExerciseMapper.toDto(traineeExercise);

        restTraineeExerciseMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traineeExerciseDTO))
            )
            .andExpect(status().isBadRequest());

        List<TraineeExercise> traineeExerciseList = traineeExerciseRepository.findAll();
        assertThat(traineeExerciseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTraineeExercises() throws Exception {
        // Initialize the database
        traineeExerciseRepository.saveAndFlush(traineeExercise);

        // Get all the traineeExerciseList
        restTraineeExerciseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traineeExercise.getId().intValue())))
            .andExpect(jsonPath("$.[*].trainee_id").value(hasItem(DEFAULT_TRAINEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].exercise_id").value(hasItem(DEFAULT_EXERCISE_ID.intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].created_Date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getTraineeExercise() throws Exception {
        // Initialize the database
        traineeExerciseRepository.saveAndFlush(traineeExercise);

        // Get the traineeExercise
        restTraineeExerciseMockMvc
            .perform(get(ENTITY_API_URL_ID, traineeExercise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(traineeExercise.getId().intValue()))
            .andExpect(jsonPath("$.trainee_id").value(DEFAULT_TRAINEE_ID.intValue()))
            .andExpect(jsonPath("$.exercise_id").value(DEFAULT_EXERCISE_ID.intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.created_Date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.intValue()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTraineeExercise() throws Exception {
        // Get the traineeExercise
        restTraineeExerciseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTraineeExercise() throws Exception {
        // Initialize the database
        traineeExerciseRepository.saveAndFlush(traineeExercise);

        int databaseSizeBeforeUpdate = traineeExerciseRepository.findAll().size();

        // Update the traineeExercise
        TraineeExercise updatedTraineeExercise = traineeExerciseRepository.findById(traineeExercise.getId()).get();
        // Disconnect from session so that the updates on updatedTraineeExercise are not directly saved in db
        em.detach(updatedTraineeExercise);
        updatedTraineeExercise
            .trainee_id(UPDATED_TRAINEE_ID)
            .exercise_id(UPDATED_EXERCISE_ID)
            .note(UPDATED_NOTE)
            .created_by(UPDATED_CREATED_BY)
            .created_Date(UPDATED_CREATED_DATE)
            .modified_by(UPDATED_MODIFIED_BY)
            .modified_date(UPDATED_MODIFIED_DATE);
        TraineeExerciseDTO traineeExerciseDTO = traineeExerciseMapper.toDto(updatedTraineeExercise);

        restTraineeExerciseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, traineeExerciseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(traineeExerciseDTO))
            )
            .andExpect(status().isOk());

        // Validate the TraineeExercise in the database
        List<TraineeExercise> traineeExerciseList = traineeExerciseRepository.findAll();
        assertThat(traineeExerciseList).hasSize(databaseSizeBeforeUpdate);
        TraineeExercise testTraineeExercise = traineeExerciseList.get(traineeExerciseList.size() - 1);
        assertThat(testTraineeExercise.getTrainee_id()).isEqualTo(UPDATED_TRAINEE_ID);
        assertThat(testTraineeExercise.getExercise_id()).isEqualTo(UPDATED_EXERCISE_ID);
        assertThat(testTraineeExercise.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testTraineeExercise.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTraineeExercise.getCreated_Date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTraineeExercise.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testTraineeExercise.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTraineeExercise() throws Exception {
        int databaseSizeBeforeUpdate = traineeExerciseRepository.findAll().size();
        traineeExercise.setId(count.incrementAndGet());

        // Create the TraineeExercise
        TraineeExerciseDTO traineeExerciseDTO = traineeExerciseMapper.toDto(traineeExercise);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTraineeExerciseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, traineeExerciseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(traineeExerciseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TraineeExercise in the database
        List<TraineeExercise> traineeExerciseList = traineeExerciseRepository.findAll();
        assertThat(traineeExerciseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTraineeExercise() throws Exception {
        int databaseSizeBeforeUpdate = traineeExerciseRepository.findAll().size();
        traineeExercise.setId(count.incrementAndGet());

        // Create the TraineeExercise
        TraineeExerciseDTO traineeExerciseDTO = traineeExerciseMapper.toDto(traineeExercise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTraineeExerciseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(traineeExerciseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TraineeExercise in the database
        List<TraineeExercise> traineeExerciseList = traineeExerciseRepository.findAll();
        assertThat(traineeExerciseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTraineeExercise() throws Exception {
        int databaseSizeBeforeUpdate = traineeExerciseRepository.findAll().size();
        traineeExercise.setId(count.incrementAndGet());

        // Create the TraineeExercise
        TraineeExerciseDTO traineeExerciseDTO = traineeExerciseMapper.toDto(traineeExercise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTraineeExerciseMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traineeExerciseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TraineeExercise in the database
        List<TraineeExercise> traineeExerciseList = traineeExerciseRepository.findAll();
        assertThat(traineeExerciseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTraineeExerciseWithPatch() throws Exception {
        // Initialize the database
        traineeExerciseRepository.saveAndFlush(traineeExercise);

        int databaseSizeBeforeUpdate = traineeExerciseRepository.findAll().size();

        // Update the traineeExercise using partial update
        TraineeExercise partialUpdatedTraineeExercise = new TraineeExercise();
        partialUpdatedTraineeExercise.setId(traineeExercise.getId());

        partialUpdatedTraineeExercise
            .trainee_id(UPDATED_TRAINEE_ID)
            .created_by(UPDATED_CREATED_BY)
            .modified_by(UPDATED_MODIFIED_BY)
            .modified_date(UPDATED_MODIFIED_DATE);

        restTraineeExerciseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTraineeExercise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTraineeExercise))
            )
            .andExpect(status().isOk());

        // Validate the TraineeExercise in the database
        List<TraineeExercise> traineeExerciseList = traineeExerciseRepository.findAll();
        assertThat(traineeExerciseList).hasSize(databaseSizeBeforeUpdate);
        TraineeExercise testTraineeExercise = traineeExerciseList.get(traineeExerciseList.size() - 1);
        assertThat(testTraineeExercise.getTrainee_id()).isEqualTo(UPDATED_TRAINEE_ID);
        assertThat(testTraineeExercise.getExercise_id()).isEqualTo(DEFAULT_EXERCISE_ID);
        assertThat(testTraineeExercise.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testTraineeExercise.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTraineeExercise.getCreated_Date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTraineeExercise.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testTraineeExercise.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTraineeExerciseWithPatch() throws Exception {
        // Initialize the database
        traineeExerciseRepository.saveAndFlush(traineeExercise);

        int databaseSizeBeforeUpdate = traineeExerciseRepository.findAll().size();

        // Update the traineeExercise using partial update
        TraineeExercise partialUpdatedTraineeExercise = new TraineeExercise();
        partialUpdatedTraineeExercise.setId(traineeExercise.getId());

        partialUpdatedTraineeExercise
            .trainee_id(UPDATED_TRAINEE_ID)
            .exercise_id(UPDATED_EXERCISE_ID)
            .note(UPDATED_NOTE)
            .created_by(UPDATED_CREATED_BY)
            .created_Date(UPDATED_CREATED_DATE)
            .modified_by(UPDATED_MODIFIED_BY)
            .modified_date(UPDATED_MODIFIED_DATE);

        restTraineeExerciseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTraineeExercise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTraineeExercise))
            )
            .andExpect(status().isOk());

        // Validate the TraineeExercise in the database
        List<TraineeExercise> traineeExerciseList = traineeExerciseRepository.findAll();
        assertThat(traineeExerciseList).hasSize(databaseSizeBeforeUpdate);
        TraineeExercise testTraineeExercise = traineeExerciseList.get(traineeExerciseList.size() - 1);
        assertThat(testTraineeExercise.getTrainee_id()).isEqualTo(UPDATED_TRAINEE_ID);
        assertThat(testTraineeExercise.getExercise_id()).isEqualTo(UPDATED_EXERCISE_ID);
        assertThat(testTraineeExercise.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testTraineeExercise.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTraineeExercise.getCreated_Date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTraineeExercise.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testTraineeExercise.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTraineeExercise() throws Exception {
        int databaseSizeBeforeUpdate = traineeExerciseRepository.findAll().size();
        traineeExercise.setId(count.incrementAndGet());

        // Create the TraineeExercise
        TraineeExerciseDTO traineeExerciseDTO = traineeExerciseMapper.toDto(traineeExercise);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTraineeExerciseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, traineeExerciseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(traineeExerciseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TraineeExercise in the database
        List<TraineeExercise> traineeExerciseList = traineeExerciseRepository.findAll();
        assertThat(traineeExerciseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTraineeExercise() throws Exception {
        int databaseSizeBeforeUpdate = traineeExerciseRepository.findAll().size();
        traineeExercise.setId(count.incrementAndGet());

        // Create the TraineeExercise
        TraineeExerciseDTO traineeExerciseDTO = traineeExerciseMapper.toDto(traineeExercise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTraineeExerciseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(traineeExerciseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TraineeExercise in the database
        List<TraineeExercise> traineeExerciseList = traineeExerciseRepository.findAll();
        assertThat(traineeExerciseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTraineeExercise() throws Exception {
        int databaseSizeBeforeUpdate = traineeExerciseRepository.findAll().size();
        traineeExercise.setId(count.incrementAndGet());

        // Create the TraineeExercise
        TraineeExerciseDTO traineeExerciseDTO = traineeExerciseMapper.toDto(traineeExercise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTraineeExerciseMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(traineeExerciseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TraineeExercise in the database
        List<TraineeExercise> traineeExerciseList = traineeExerciseRepository.findAll();
        assertThat(traineeExerciseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTraineeExercise() throws Exception {
        // Initialize the database
        traineeExerciseRepository.saveAndFlush(traineeExercise);

        int databaseSizeBeforeDelete = traineeExerciseRepository.findAll().size();

        // Delete the traineeExercise
        restTraineeExerciseMockMvc
            .perform(delete(ENTITY_API_URL_ID, traineeExercise.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TraineeExercise> traineeExerciseList = traineeExerciseRepository.findAll();
        assertThat(traineeExerciseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
