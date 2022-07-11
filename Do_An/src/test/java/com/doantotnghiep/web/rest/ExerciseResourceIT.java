package com.doantotnghiep.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.doantotnghiep.IntegrationTest;
import com.doantotnghiep.domain.Exercise;
import com.doantotnghiep.repository.ExerciseRepository;
import com.doantotnghiep.service.dto.ExerciseDTO;
import com.doantotnghiep.service.mapper.ExerciseMapper;
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
 * Integration tests for the {@link ExerciseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExerciseResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_IS_ACTIVE = 1;
    private static final Integer UPDATED_IS_ACTIVE = 2;

    private static final String ENTITY_API_URL = "/api/exercises";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ExerciseMapper exerciseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExerciseMockMvc;

    private Exercise exercise;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exercise createEntity(EntityManager em) {
        Exercise exercise = new Exercise()
            .title(DEFAULT_TITLE)
            .short_Description(DEFAULT_SHORT_DESCRIPTION)
            .content(DEFAULT_CONTENT)
            .created_by(DEFAULT_CREATED_BY)
            .updated_by(DEFAULT_UPDATED_BY)
            .image(DEFAULT_IMAGE)
            .is_active(DEFAULT_IS_ACTIVE);
        return exercise;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exercise createUpdatedEntity(EntityManager em) {
        Exercise exercise = new Exercise()
            .title(UPDATED_TITLE)
            .short_Description(UPDATED_SHORT_DESCRIPTION)
            .content(UPDATED_CONTENT)
            .created_by(UPDATED_CREATED_BY)
            .updated_by(UPDATED_UPDATED_BY)
            .image(UPDATED_IMAGE)
            .is_active(UPDATED_IS_ACTIVE);
        return exercise;
    }

    @BeforeEach
    public void initTest() {
        exercise = createEntity(em);
    }

    @Test
    @Transactional
    void createExercise() throws Exception {
        int databaseSizeBeforeCreate = exerciseRepository.findAll().size();
        // Create the Exercise
        ExerciseDTO exerciseDTO = exerciseMapper.toDto(exercise);
        restExerciseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exerciseDTO)))
            .andExpect(status().isCreated());

        // Validate the Exercise in the database
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeCreate + 1);
        Exercise testExercise = exerciseList.get(exerciseList.size() - 1);
        assertThat(testExercise.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testExercise.getShort_Description()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testExercise.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testExercise.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testExercise.getUpdated_by()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testExercise.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testExercise.getIs_active()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void createExerciseWithExistingId() throws Exception {
        // Create the Exercise with an existing ID
        exercise.setId(1L);
        ExerciseDTO exerciseDTO = exerciseMapper.toDto(exercise);

        int databaseSizeBeforeCreate = exerciseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExerciseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exerciseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Exercise in the database
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseRepository.findAll().size();
        // set the field null
        exercise.setTitle(null);

        // Create the Exercise, which fails.
        ExerciseDTO exerciseDTO = exerciseMapper.toDto(exercise);

        restExerciseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exerciseDTO)))
            .andExpect(status().isBadRequest());

        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseRepository.findAll().size();
        // set the field null
        exercise.setContent(null);

        // Create the Exercise, which fails.
        ExerciseDTO exerciseDTO = exerciseMapper.toDto(exercise);

        restExerciseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exerciseDTO)))
            .andExpect(status().isBadRequest());

        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseRepository.findAll().size();
        // set the field null
        exercise.setCreated_by(null);

        // Create the Exercise, which fails.
        ExerciseDTO exerciseDTO = exerciseMapper.toDto(exercise);

        restExerciseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exerciseDTO)))
            .andExpect(status().isBadRequest());

        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkImageIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseRepository.findAll().size();
        // set the field null
        exercise.setImage(null);

        // Create the Exercise, which fails.
        ExerciseDTO exerciseDTO = exerciseMapper.toDto(exercise);

        restExerciseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exerciseDTO)))
            .andExpect(status().isBadRequest());

        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIs_activeIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseRepository.findAll().size();
        // set the field null
        exercise.setIs_active(null);

        // Create the Exercise, which fails.
        ExerciseDTO exerciseDTO = exerciseMapper.toDto(exercise);

        restExerciseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exerciseDTO)))
            .andExpect(status().isBadRequest());

        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllExercises() throws Exception {
        // Initialize the database
        exerciseRepository.saveAndFlush(exercise);

        // Get all the exerciseList
        restExerciseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exercise.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].short_Description").value(hasItem(DEFAULT_SHORT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updated_by").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].is_active").value(hasItem(DEFAULT_IS_ACTIVE)));
    }

    @Test
    @Transactional
    void getExercise() throws Exception {
        // Initialize the database
        exerciseRepository.saveAndFlush(exercise);

        // Get the exercise
        restExerciseMockMvc
            .perform(get(ENTITY_API_URL_ID, exercise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(exercise.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.short_Description").value(DEFAULT_SHORT_DESCRIPTION))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.updated_by").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE))
            .andExpect(jsonPath("$.is_active").value(DEFAULT_IS_ACTIVE));
    }

    @Test
    @Transactional
    void getNonExistingExercise() throws Exception {
        // Get the exercise
        restExerciseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewExercise() throws Exception {
        // Initialize the database
        exerciseRepository.saveAndFlush(exercise);

        int databaseSizeBeforeUpdate = exerciseRepository.findAll().size();

        // Update the exercise
        Exercise updatedExercise = exerciseRepository.findById(exercise.getId()).get();
        // Disconnect from session so that the updates on updatedExercise are not directly saved in db
        em.detach(updatedExercise);
        updatedExercise
            .title(UPDATED_TITLE)
            .short_Description(UPDATED_SHORT_DESCRIPTION)
            .content(UPDATED_CONTENT)
            .created_by(UPDATED_CREATED_BY)
            .updated_by(UPDATED_UPDATED_BY)
            .image(UPDATED_IMAGE)
            .is_active(UPDATED_IS_ACTIVE);
        ExerciseDTO exerciseDTO = exerciseMapper.toDto(updatedExercise);

        restExerciseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, exerciseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(exerciseDTO))
            )
            .andExpect(status().isOk());

        // Validate the Exercise in the database
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeUpdate);
        Exercise testExercise = exerciseList.get(exerciseList.size() - 1);
        assertThat(testExercise.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testExercise.getShort_Description()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testExercise.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testExercise.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testExercise.getUpdated_by()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testExercise.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testExercise.getIs_active()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingExercise() throws Exception {
        int databaseSizeBeforeUpdate = exerciseRepository.findAll().size();
        exercise.setId(count.incrementAndGet());

        // Create the Exercise
        ExerciseDTO exerciseDTO = exerciseMapper.toDto(exercise);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExerciseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, exerciseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(exerciseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Exercise in the database
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExercise() throws Exception {
        int databaseSizeBeforeUpdate = exerciseRepository.findAll().size();
        exercise.setId(count.incrementAndGet());

        // Create the Exercise
        ExerciseDTO exerciseDTO = exerciseMapper.toDto(exercise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExerciseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(exerciseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Exercise in the database
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExercise() throws Exception {
        int databaseSizeBeforeUpdate = exerciseRepository.findAll().size();
        exercise.setId(count.incrementAndGet());

        // Create the Exercise
        ExerciseDTO exerciseDTO = exerciseMapper.toDto(exercise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExerciseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exerciseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Exercise in the database
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExerciseWithPatch() throws Exception {
        // Initialize the database
        exerciseRepository.saveAndFlush(exercise);

        int databaseSizeBeforeUpdate = exerciseRepository.findAll().size();

        // Update the exercise using partial update
        Exercise partialUpdatedExercise = new Exercise();
        partialUpdatedExercise.setId(exercise.getId());

        partialUpdatedExercise.short_Description(UPDATED_SHORT_DESCRIPTION).updated_by(UPDATED_UPDATED_BY);

        restExerciseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExercise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExercise))
            )
            .andExpect(status().isOk());

        // Validate the Exercise in the database
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeUpdate);
        Exercise testExercise = exerciseList.get(exerciseList.size() - 1);
        assertThat(testExercise.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testExercise.getShort_Description()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testExercise.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testExercise.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testExercise.getUpdated_by()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testExercise.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testExercise.getIs_active()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateExerciseWithPatch() throws Exception {
        // Initialize the database
        exerciseRepository.saveAndFlush(exercise);

        int databaseSizeBeforeUpdate = exerciseRepository.findAll().size();

        // Update the exercise using partial update
        Exercise partialUpdatedExercise = new Exercise();
        partialUpdatedExercise.setId(exercise.getId());

        partialUpdatedExercise
            .title(UPDATED_TITLE)
            .short_Description(UPDATED_SHORT_DESCRIPTION)
            .content(UPDATED_CONTENT)
            .created_by(UPDATED_CREATED_BY)
            .updated_by(UPDATED_UPDATED_BY)
            .image(UPDATED_IMAGE)
            .is_active(UPDATED_IS_ACTIVE);

        restExerciseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExercise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExercise))
            )
            .andExpect(status().isOk());

        // Validate the Exercise in the database
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeUpdate);
        Exercise testExercise = exerciseList.get(exerciseList.size() - 1);
        assertThat(testExercise.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testExercise.getShort_Description()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testExercise.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testExercise.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testExercise.getUpdated_by()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testExercise.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testExercise.getIs_active()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingExercise() throws Exception {
        int databaseSizeBeforeUpdate = exerciseRepository.findAll().size();
        exercise.setId(count.incrementAndGet());

        // Create the Exercise
        ExerciseDTO exerciseDTO = exerciseMapper.toDto(exercise);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExerciseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, exerciseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(exerciseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Exercise in the database
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExercise() throws Exception {
        int databaseSizeBeforeUpdate = exerciseRepository.findAll().size();
        exercise.setId(count.incrementAndGet());

        // Create the Exercise
        ExerciseDTO exerciseDTO = exerciseMapper.toDto(exercise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExerciseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(exerciseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Exercise in the database
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExercise() throws Exception {
        int databaseSizeBeforeUpdate = exerciseRepository.findAll().size();
        exercise.setId(count.incrementAndGet());

        // Create the Exercise
        ExerciseDTO exerciseDTO = exerciseMapper.toDto(exercise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExerciseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(exerciseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Exercise in the database
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExercise() throws Exception {
        // Initialize the database
        exerciseRepository.saveAndFlush(exercise);

        int databaseSizeBeforeDelete = exerciseRepository.findAll().size();

        // Delete the exercise
        restExerciseMockMvc
            .perform(delete(ENTITY_API_URL_ID, exercise.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
