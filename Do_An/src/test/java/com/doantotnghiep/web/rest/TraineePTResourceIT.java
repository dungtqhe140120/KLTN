package com.doantotnghiep.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.doantotnghiep.IntegrationTest;
import com.doantotnghiep.domain.TraineePT;
import com.doantotnghiep.repository.TraineePTRepository;
import com.doantotnghiep.service.dto.TraineePTDTO;
import com.doantotnghiep.service.mapper.TraineePTMapper;
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
 * Integration tests for the {@link TraineePTResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TraineePTResourceIT {

    private static final Long DEFAULT_PERSONAL_TRAINER_ID = 1L;
    private static final Long UPDATED_PERSONAL_TRAINER_ID = 2L;

    private static final Long DEFAULT_TRAINEE_ID = 1L;
    private static final Long UPDATED_TRAINEE_ID = 2L;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/trainee-pts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TraineePTRepository traineePTRepository;

    @Autowired
    private TraineePTMapper traineePTMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTraineePTMockMvc;

    private TraineePT traineePT;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TraineePT createEntity(EntityManager em) {
        TraineePT traineePT = new TraineePT()
            .personalTrainer_id(DEFAULT_PERSONAL_TRAINER_ID)
            .trainee_id(DEFAULT_TRAINEE_ID)
            .type(DEFAULT_TYPE);
        return traineePT;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TraineePT createUpdatedEntity(EntityManager em) {
        TraineePT traineePT = new TraineePT()
            .personalTrainer_id(UPDATED_PERSONAL_TRAINER_ID)
            .trainee_id(UPDATED_TRAINEE_ID)
            .type(UPDATED_TYPE);
        return traineePT;
    }

    @BeforeEach
    public void initTest() {
        traineePT = createEntity(em);
    }

    @Test
    @Transactional
    void createTraineePT() throws Exception {
        int databaseSizeBeforeCreate = traineePTRepository.findAll().size();
        // Create the TraineePT
        TraineePTDTO traineePTDTO = traineePTMapper.toDto(traineePT);
        restTraineePTMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traineePTDTO)))
            .andExpect(status().isCreated());

        // Validate the TraineePT in the database
        List<TraineePT> traineePTList = traineePTRepository.findAll();
        assertThat(traineePTList).hasSize(databaseSizeBeforeCreate + 1);
        TraineePT testTraineePT = traineePTList.get(traineePTList.size() - 1);
        assertThat(testTraineePT.getPersonalTrainer_id()).isEqualTo(DEFAULT_PERSONAL_TRAINER_ID);
        assertThat(testTraineePT.getTrainee_id()).isEqualTo(DEFAULT_TRAINEE_ID);
        assertThat(testTraineePT.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createTraineePTWithExistingId() throws Exception {
        // Create the TraineePT with an existing ID
        traineePT.setId(1L);
        TraineePTDTO traineePTDTO = traineePTMapper.toDto(traineePT);

        int databaseSizeBeforeCreate = traineePTRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraineePTMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traineePTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TraineePT in the database
        List<TraineePT> traineePTList = traineePTRepository.findAll();
        assertThat(traineePTList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPersonalTrainer_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = traineePTRepository.findAll().size();
        // set the field null
        traineePT.setPersonalTrainer_id(null);

        // Create the TraineePT, which fails.
        TraineePTDTO traineePTDTO = traineePTMapper.toDto(traineePT);

        restTraineePTMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traineePTDTO)))
            .andExpect(status().isBadRequest());

        List<TraineePT> traineePTList = traineePTRepository.findAll();
        assertThat(traineePTList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTrainee_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = traineePTRepository.findAll().size();
        // set the field null
        traineePT.setTrainee_id(null);

        // Create the TraineePT, which fails.
        TraineePTDTO traineePTDTO = traineePTMapper.toDto(traineePT);

        restTraineePTMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traineePTDTO)))
            .andExpect(status().isBadRequest());

        List<TraineePT> traineePTList = traineePTRepository.findAll();
        assertThat(traineePTList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTraineePTS() throws Exception {
        // Initialize the database
        traineePTRepository.saveAndFlush(traineePT);

        // Get all the traineePTList
        restTraineePTMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traineePT.getId().intValue())))
            .andExpect(jsonPath("$.[*].personalTrainer_id").value(hasItem(DEFAULT_PERSONAL_TRAINER_ID.intValue())))
            .andExpect(jsonPath("$.[*].trainee_id").value(hasItem(DEFAULT_TRAINEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getTraineePT() throws Exception {
        // Initialize the database
        traineePTRepository.saveAndFlush(traineePT);

        // Get the traineePT
        restTraineePTMockMvc
            .perform(get(ENTITY_API_URL_ID, traineePT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(traineePT.getId().intValue()))
            .andExpect(jsonPath("$.personalTrainer_id").value(DEFAULT_PERSONAL_TRAINER_ID.intValue()))
            .andExpect(jsonPath("$.trainee_id").value(DEFAULT_TRAINEE_ID.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingTraineePT() throws Exception {
        // Get the traineePT
        restTraineePTMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTraineePT() throws Exception {
        // Initialize the database
        traineePTRepository.saveAndFlush(traineePT);

        int databaseSizeBeforeUpdate = traineePTRepository.findAll().size();

        // Update the traineePT
        TraineePT updatedTraineePT = traineePTRepository.findById(traineePT.getId()).get();
        // Disconnect from session so that the updates on updatedTraineePT are not directly saved in db
        em.detach(updatedTraineePT);
        updatedTraineePT.personalTrainer_id(UPDATED_PERSONAL_TRAINER_ID).trainee_id(UPDATED_TRAINEE_ID).type(UPDATED_TYPE);
        TraineePTDTO traineePTDTO = traineePTMapper.toDto(updatedTraineePT);

        restTraineePTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, traineePTDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(traineePTDTO))
            )
            .andExpect(status().isOk());

        // Validate the TraineePT in the database
        List<TraineePT> traineePTList = traineePTRepository.findAll();
        assertThat(traineePTList).hasSize(databaseSizeBeforeUpdate);
        TraineePT testTraineePT = traineePTList.get(traineePTList.size() - 1);
        assertThat(testTraineePT.getPersonalTrainer_id()).isEqualTo(UPDATED_PERSONAL_TRAINER_ID);
        assertThat(testTraineePT.getTrainee_id()).isEqualTo(UPDATED_TRAINEE_ID);
        assertThat(testTraineePT.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingTraineePT() throws Exception {
        int databaseSizeBeforeUpdate = traineePTRepository.findAll().size();
        traineePT.setId(count.incrementAndGet());

        // Create the TraineePT
        TraineePTDTO traineePTDTO = traineePTMapper.toDto(traineePT);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTraineePTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, traineePTDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(traineePTDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TraineePT in the database
        List<TraineePT> traineePTList = traineePTRepository.findAll();
        assertThat(traineePTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTraineePT() throws Exception {
        int databaseSizeBeforeUpdate = traineePTRepository.findAll().size();
        traineePT.setId(count.incrementAndGet());

        // Create the TraineePT
        TraineePTDTO traineePTDTO = traineePTMapper.toDto(traineePT);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTraineePTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(traineePTDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TraineePT in the database
        List<TraineePT> traineePTList = traineePTRepository.findAll();
        assertThat(traineePTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTraineePT() throws Exception {
        int databaseSizeBeforeUpdate = traineePTRepository.findAll().size();
        traineePT.setId(count.incrementAndGet());

        // Create the TraineePT
        TraineePTDTO traineePTDTO = traineePTMapper.toDto(traineePT);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTraineePTMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traineePTDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TraineePT in the database
        List<TraineePT> traineePTList = traineePTRepository.findAll();
        assertThat(traineePTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTraineePTWithPatch() throws Exception {
        // Initialize the database
        traineePTRepository.saveAndFlush(traineePT);

        int databaseSizeBeforeUpdate = traineePTRepository.findAll().size();

        // Update the traineePT using partial update
        TraineePT partialUpdatedTraineePT = new TraineePT();
        partialUpdatedTraineePT.setId(traineePT.getId());

        partialUpdatedTraineePT.personalTrainer_id(UPDATED_PERSONAL_TRAINER_ID).trainee_id(UPDATED_TRAINEE_ID).type(UPDATED_TYPE);

        restTraineePTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTraineePT.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTraineePT))
            )
            .andExpect(status().isOk());

        // Validate the TraineePT in the database
        List<TraineePT> traineePTList = traineePTRepository.findAll();
        assertThat(traineePTList).hasSize(databaseSizeBeforeUpdate);
        TraineePT testTraineePT = traineePTList.get(traineePTList.size() - 1);
        assertThat(testTraineePT.getPersonalTrainer_id()).isEqualTo(UPDATED_PERSONAL_TRAINER_ID);
        assertThat(testTraineePT.getTrainee_id()).isEqualTo(UPDATED_TRAINEE_ID);
        assertThat(testTraineePT.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateTraineePTWithPatch() throws Exception {
        // Initialize the database
        traineePTRepository.saveAndFlush(traineePT);

        int databaseSizeBeforeUpdate = traineePTRepository.findAll().size();

        // Update the traineePT using partial update
        TraineePT partialUpdatedTraineePT = new TraineePT();
        partialUpdatedTraineePT.setId(traineePT.getId());

        partialUpdatedTraineePT.personalTrainer_id(UPDATED_PERSONAL_TRAINER_ID).trainee_id(UPDATED_TRAINEE_ID).type(UPDATED_TYPE);

        restTraineePTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTraineePT.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTraineePT))
            )
            .andExpect(status().isOk());

        // Validate the TraineePT in the database
        List<TraineePT> traineePTList = traineePTRepository.findAll();
        assertThat(traineePTList).hasSize(databaseSizeBeforeUpdate);
        TraineePT testTraineePT = traineePTList.get(traineePTList.size() - 1);
        assertThat(testTraineePT.getPersonalTrainer_id()).isEqualTo(UPDATED_PERSONAL_TRAINER_ID);
        assertThat(testTraineePT.getTrainee_id()).isEqualTo(UPDATED_TRAINEE_ID);
        assertThat(testTraineePT.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingTraineePT() throws Exception {
        int databaseSizeBeforeUpdate = traineePTRepository.findAll().size();
        traineePT.setId(count.incrementAndGet());

        // Create the TraineePT
        TraineePTDTO traineePTDTO = traineePTMapper.toDto(traineePT);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTraineePTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, traineePTDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(traineePTDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TraineePT in the database
        List<TraineePT> traineePTList = traineePTRepository.findAll();
        assertThat(traineePTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTraineePT() throws Exception {
        int databaseSizeBeforeUpdate = traineePTRepository.findAll().size();
        traineePT.setId(count.incrementAndGet());

        // Create the TraineePT
        TraineePTDTO traineePTDTO = traineePTMapper.toDto(traineePT);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTraineePTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(traineePTDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TraineePT in the database
        List<TraineePT> traineePTList = traineePTRepository.findAll();
        assertThat(traineePTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTraineePT() throws Exception {
        int databaseSizeBeforeUpdate = traineePTRepository.findAll().size();
        traineePT.setId(count.incrementAndGet());

        // Create the TraineePT
        TraineePTDTO traineePTDTO = traineePTMapper.toDto(traineePT);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTraineePTMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(traineePTDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TraineePT in the database
        List<TraineePT> traineePTList = traineePTRepository.findAll();
        assertThat(traineePTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTraineePT() throws Exception {
        // Initialize the database
        traineePTRepository.saveAndFlush(traineePT);

        int databaseSizeBeforeDelete = traineePTRepository.findAll().size();

        // Delete the traineePT
        restTraineePTMockMvc
            .perform(delete(ENTITY_API_URL_ID, traineePT.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TraineePT> traineePTList = traineePTRepository.findAll();
        assertThat(traineePTList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
