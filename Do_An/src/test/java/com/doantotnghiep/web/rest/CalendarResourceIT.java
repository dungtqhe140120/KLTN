package com.doantotnghiep.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.doantotnghiep.IntegrationTest;
import com.doantotnghiep.domain.Calendar;
import com.doantotnghiep.repository.CalendarRepository;
import com.doantotnghiep.service.dto.CalendarDTO;
import com.doantotnghiep.service.mapper.CalendarMapper;
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
 * Integration tests for the {@link CalendarResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CalendarResourceIT {

    private static final LocalDate DEFAULT_CALENDAR_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CALENDAR_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CALENDAR_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CALENDAR_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/calendars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private CalendarMapper calendarMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCalendarMockMvc;

    private Calendar calendar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calendar createEntity(EntityManager em) {
        Calendar calendar = new Calendar()
            .calendar_date(DEFAULT_CALENDAR_DATE)
            .user_id(DEFAULT_USER_ID)
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .calendar_time(DEFAULT_CALENDAR_TIME);
        return calendar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calendar createUpdatedEntity(EntityManager em) {
        Calendar calendar = new Calendar()
            .calendar_date(UPDATED_CALENDAR_DATE)
            .user_id(UPDATED_USER_ID)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .calendar_time(UPDATED_CALENDAR_TIME);
        return calendar;
    }

    @BeforeEach
    public void initTest() {
        calendar = createEntity(em);
    }

    @Test
    @Transactional
    void createCalendar() throws Exception {
        int databaseSizeBeforeCreate = calendarRepository.findAll().size();
        // Create the Calendar
        CalendarDTO calendarDTO = calendarMapper.toDto(calendar);
        restCalendarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(calendarDTO)))
            .andExpect(status().isCreated());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeCreate + 1);
        Calendar testCalendar = calendarList.get(calendarList.size() - 1);
        assertThat(testCalendar.getCalendar_date()).isEqualTo(DEFAULT_CALENDAR_DATE);
        assertThat(testCalendar.getUser_id()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testCalendar.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCalendar.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testCalendar.getCalendar_time()).isEqualTo(DEFAULT_CALENDAR_TIME);
    }

    @Test
    @Transactional
    void createCalendarWithExistingId() throws Exception {
        // Create the Calendar with an existing ID
        calendar.setId(1L);
        CalendarDTO calendarDTO = calendarMapper.toDto(calendar);

        int databaseSizeBeforeCreate = calendarRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalendarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(calendarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCalendar_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarRepository.findAll().size();
        // set the field null
        calendar.setCalendar_date(null);

        // Create the Calendar, which fails.
        CalendarDTO calendarDTO = calendarMapper.toDto(calendar);

        restCalendarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(calendarDTO)))
            .andExpect(status().isBadRequest());

        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUser_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarRepository.findAll().size();
        // set the field null
        calendar.setUser_id(null);

        // Create the Calendar, which fails.
        CalendarDTO calendarDTO = calendarMapper.toDto(calendar);

        restCalendarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(calendarDTO)))
            .andExpect(status().isBadRequest());

        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarRepository.findAll().size();
        // set the field null
        calendar.setTitle(null);

        // Create the Calendar, which fails.
        CalendarDTO calendarDTO = calendarMapper.toDto(calendar);

        restCalendarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(calendarDTO)))
            .andExpect(status().isBadRequest());

        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCalendar_timeIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarRepository.findAll().size();
        // set the field null
        calendar.setCalendar_time(null);

        // Create the Calendar, which fails.
        CalendarDTO calendarDTO = calendarMapper.toDto(calendar);

        restCalendarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(calendarDTO)))
            .andExpect(status().isBadRequest());

        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCalendars() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList
        restCalendarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendar.getId().intValue())))
            .andExpect(jsonPath("$.[*].calendar_date").value(hasItem(DEFAULT_CALENDAR_DATE.toString())))
            .andExpect(jsonPath("$.[*].user_id").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].calendar_time").value(hasItem(DEFAULT_CALENDAR_TIME.toString())));
    }

    @Test
    @Transactional
    void getCalendar() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get the calendar
        restCalendarMockMvc
            .perform(get(ENTITY_API_URL_ID, calendar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(calendar.getId().intValue()))
            .andExpect(jsonPath("$.calendar_date").value(DEFAULT_CALENDAR_DATE.toString()))
            .andExpect(jsonPath("$.user_id").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.calendar_time").value(DEFAULT_CALENDAR_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCalendar() throws Exception {
        // Get the calendar
        restCalendarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCalendar() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();

        // Update the calendar
        Calendar updatedCalendar = calendarRepository.findById(calendar.getId()).get();
        // Disconnect from session so that the updates on updatedCalendar are not directly saved in db
        em.detach(updatedCalendar);
        updatedCalendar
            .calendar_date(UPDATED_CALENDAR_DATE)
            .user_id(UPDATED_USER_ID)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .calendar_time(UPDATED_CALENDAR_TIME);
        CalendarDTO calendarDTO = calendarMapper.toDto(updatedCalendar);

        restCalendarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, calendarDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(calendarDTO))
            )
            .andExpect(status().isOk());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
        Calendar testCalendar = calendarList.get(calendarList.size() - 1);
        assertThat(testCalendar.getCalendar_date()).isEqualTo(UPDATED_CALENDAR_DATE);
        assertThat(testCalendar.getUser_id()).isEqualTo(UPDATED_USER_ID);
        assertThat(testCalendar.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCalendar.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testCalendar.getCalendar_time()).isEqualTo(UPDATED_CALENDAR_TIME);
    }

    @Test
    @Transactional
    void putNonExistingCalendar() throws Exception {
        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();
        calendar.setId(count.incrementAndGet());

        // Create the Calendar
        CalendarDTO calendarDTO = calendarMapper.toDto(calendar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalendarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, calendarDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(calendarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCalendar() throws Exception {
        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();
        calendar.setId(count.incrementAndGet());

        // Create the Calendar
        CalendarDTO calendarDTO = calendarMapper.toDto(calendar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalendarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(calendarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCalendar() throws Exception {
        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();
        calendar.setId(count.incrementAndGet());

        // Create the Calendar
        CalendarDTO calendarDTO = calendarMapper.toDto(calendar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalendarMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(calendarDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCalendarWithPatch() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();

        // Update the calendar using partial update
        Calendar partialUpdatedCalendar = new Calendar();
        partialUpdatedCalendar.setId(calendar.getId());

        partialUpdatedCalendar.calendar_date(UPDATED_CALENDAR_DATE).user_id(UPDATED_USER_ID).calendar_time(UPDATED_CALENDAR_TIME);

        restCalendarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCalendar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCalendar))
            )
            .andExpect(status().isOk());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
        Calendar testCalendar = calendarList.get(calendarList.size() - 1);
        assertThat(testCalendar.getCalendar_date()).isEqualTo(UPDATED_CALENDAR_DATE);
        assertThat(testCalendar.getUser_id()).isEqualTo(UPDATED_USER_ID);
        assertThat(testCalendar.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCalendar.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testCalendar.getCalendar_time()).isEqualTo(UPDATED_CALENDAR_TIME);
    }

    @Test
    @Transactional
    void fullUpdateCalendarWithPatch() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();

        // Update the calendar using partial update
        Calendar partialUpdatedCalendar = new Calendar();
        partialUpdatedCalendar.setId(calendar.getId());

        partialUpdatedCalendar
            .calendar_date(UPDATED_CALENDAR_DATE)
            .user_id(UPDATED_USER_ID)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .calendar_time(UPDATED_CALENDAR_TIME);

        restCalendarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCalendar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCalendar))
            )
            .andExpect(status().isOk());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
        Calendar testCalendar = calendarList.get(calendarList.size() - 1);
        assertThat(testCalendar.getCalendar_date()).isEqualTo(UPDATED_CALENDAR_DATE);
        assertThat(testCalendar.getUser_id()).isEqualTo(UPDATED_USER_ID);
        assertThat(testCalendar.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCalendar.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testCalendar.getCalendar_time()).isEqualTo(UPDATED_CALENDAR_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingCalendar() throws Exception {
        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();
        calendar.setId(count.incrementAndGet());

        // Create the Calendar
        CalendarDTO calendarDTO = calendarMapper.toDto(calendar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalendarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, calendarDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(calendarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCalendar() throws Exception {
        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();
        calendar.setId(count.incrementAndGet());

        // Create the Calendar
        CalendarDTO calendarDTO = calendarMapper.toDto(calendar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalendarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(calendarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCalendar() throws Exception {
        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();
        calendar.setId(count.incrementAndGet());

        // Create the Calendar
        CalendarDTO calendarDTO = calendarMapper.toDto(calendar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalendarMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(calendarDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCalendar() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        int databaseSizeBeforeDelete = calendarRepository.findAll().size();

        // Delete the calendar
        restCalendarMockMvc
            .perform(delete(ENTITY_API_URL_ID, calendar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
