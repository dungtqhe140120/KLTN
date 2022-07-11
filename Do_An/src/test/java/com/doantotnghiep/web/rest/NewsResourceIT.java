package com.doantotnghiep.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.doantotnghiep.IntegrationTest;
import com.doantotnghiep.domain.News;
import com.doantotnghiep.repository.NewsRepository;
import com.doantotnghiep.service.dto.NewsDTO;
import com.doantotnghiep.service.mapper.NewsMapper;
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
 * Integration tests for the {@link NewsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NewsResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_IS_DISPLAY = 1;
    private static final Integer UPDATED_IS_DISPLAY = 2;

    private static final String ENTITY_API_URL = "/api/news";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNewsMockMvc;

    private News news;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static News createEntity(EntityManager em) {
        News news = new News()
            .title(DEFAULT_TITLE)
            .short_Description(DEFAULT_SHORT_DESCRIPTION)
            .content(DEFAULT_CONTENT)
            .created_date(DEFAULT_CREATED_DATE)
            .created_by(DEFAULT_CREATED_BY)
            .image(DEFAULT_IMAGE)
            .is_display(DEFAULT_IS_DISPLAY);
        return news;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static News createUpdatedEntity(EntityManager em) {
        News news = new News()
            .title(UPDATED_TITLE)
            .short_Description(UPDATED_SHORT_DESCRIPTION)
            .content(UPDATED_CONTENT)
            .created_date(UPDATED_CREATED_DATE)
            .created_by(UPDATED_CREATED_BY)
            .image(UPDATED_IMAGE)
            .is_display(UPDATED_IS_DISPLAY);
        return news;
    }

    @BeforeEach
    public void initTest() {
        news = createEntity(em);
    }

    @Test
    @Transactional
    void createNews() throws Exception {
        int databaseSizeBeforeCreate = newsRepository.findAll().size();
        // Create the News
        NewsDTO newsDTO = newsMapper.toDto(news);
        restNewsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isCreated());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeCreate + 1);
        News testNews = newsList.get(newsList.size() - 1);
        assertThat(testNews.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testNews.getShort_Description()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testNews.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testNews.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testNews.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testNews.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testNews.getIs_display()).isEqualTo(DEFAULT_IS_DISPLAY);
    }

    @Test
    @Transactional
    void createNewsWithExistingId() throws Exception {
        // Create the News with an existing ID
        news.setId(1L);
        NewsDTO newsDTO = newsMapper.toDto(news);

        int databaseSizeBeforeCreate = newsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNewsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsRepository.findAll().size();
        // set the field null
        news.setTitle(null);

        // Create the News, which fails.
        NewsDTO newsDTO = newsMapper.toDto(news);

        restNewsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsRepository.findAll().size();
        // set the field null
        news.setContent(null);

        // Create the News, which fails.
        NewsDTO newsDTO = newsMapper.toDto(news);

        restNewsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsRepository.findAll().size();
        // set the field null
        news.setCreated_by(null);

        // Create the News, which fails.
        NewsDTO newsDTO = newsMapper.toDto(news);

        restNewsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkImageIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsRepository.findAll().size();
        // set the field null
        news.setImage(null);

        // Create the News, which fails.
        NewsDTO newsDTO = newsMapper.toDto(news);

        restNewsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIs_displayIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsRepository.findAll().size();
        // set the field null
        news.setIs_display(null);

        // Create the News, which fails.
        NewsDTO newsDTO = newsMapper.toDto(news);

        restNewsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList
        restNewsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(news.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].short_Description").value(hasItem(DEFAULT_SHORT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].is_display").value(hasItem(DEFAULT_IS_DISPLAY)));
    }

    @Test
    @Transactional
    void getNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get the news
        restNewsMockMvc
            .perform(get(ENTITY_API_URL_ID, news.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(news.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.short_Description").value(DEFAULT_SHORT_DESCRIPTION))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE))
            .andExpect(jsonPath("$.is_display").value(DEFAULT_IS_DISPLAY));
    }

    @Test
    @Transactional
    void getNonExistingNews() throws Exception {
        // Get the news
        restNewsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        int databaseSizeBeforeUpdate = newsRepository.findAll().size();

        // Update the news
        News updatedNews = newsRepository.findById(news.getId()).get();
        // Disconnect from session so that the updates on updatedNews are not directly saved in db
        em.detach(updatedNews);
        updatedNews
            .title(UPDATED_TITLE)
            .short_Description(UPDATED_SHORT_DESCRIPTION)
            .content(UPDATED_CONTENT)
            .created_date(UPDATED_CREATED_DATE)
            .created_by(UPDATED_CREATED_BY)
            .image(UPDATED_IMAGE)
            .is_display(UPDATED_IS_DISPLAY);
        NewsDTO newsDTO = newsMapper.toDto(updatedNews);

        restNewsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, newsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(newsDTO))
            )
            .andExpect(status().isOk());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
        News testNews = newsList.get(newsList.size() - 1);
        assertThat(testNews.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNews.getShort_Description()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testNews.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testNews.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNews.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNews.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testNews.getIs_display()).isEqualTo(UPDATED_IS_DISPLAY);
    }

    @Test
    @Transactional
    void putNonExistingNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();
        news.setId(count.incrementAndGet());

        // Create the News
        NewsDTO newsDTO = newsMapper.toDto(news);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, newsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(newsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();
        news.setId(count.incrementAndGet());

        // Create the News
        NewsDTO newsDTO = newsMapper.toDto(news);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(newsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();
        news.setId(count.incrementAndGet());

        // Create the News
        NewsDTO newsDTO = newsMapper.toDto(news);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNewsWithPatch() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        int databaseSizeBeforeUpdate = newsRepository.findAll().size();

        // Update the news using partial update
        News partialUpdatedNews = new News();
        partialUpdatedNews.setId(news.getId());

        partialUpdatedNews
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .created_date(UPDATED_CREATED_DATE)
            .created_by(UPDATED_CREATED_BY)
            .image(UPDATED_IMAGE)
            .is_display(UPDATED_IS_DISPLAY);

        restNewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNews.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNews))
            )
            .andExpect(status().isOk());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
        News testNews = newsList.get(newsList.size() - 1);
        assertThat(testNews.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNews.getShort_Description()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testNews.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testNews.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNews.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNews.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testNews.getIs_display()).isEqualTo(UPDATED_IS_DISPLAY);
    }

    @Test
    @Transactional
    void fullUpdateNewsWithPatch() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        int databaseSizeBeforeUpdate = newsRepository.findAll().size();

        // Update the news using partial update
        News partialUpdatedNews = new News();
        partialUpdatedNews.setId(news.getId());

        partialUpdatedNews
            .title(UPDATED_TITLE)
            .short_Description(UPDATED_SHORT_DESCRIPTION)
            .content(UPDATED_CONTENT)
            .created_date(UPDATED_CREATED_DATE)
            .created_by(UPDATED_CREATED_BY)
            .image(UPDATED_IMAGE)
            .is_display(UPDATED_IS_DISPLAY);

        restNewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNews.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNews))
            )
            .andExpect(status().isOk());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
        News testNews = newsList.get(newsList.size() - 1);
        assertThat(testNews.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNews.getShort_Description()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testNews.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testNews.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNews.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNews.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testNews.getIs_display()).isEqualTo(UPDATED_IS_DISPLAY);
    }

    @Test
    @Transactional
    void patchNonExistingNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();
        news.setId(count.incrementAndGet());

        // Create the News
        NewsDTO newsDTO = newsMapper.toDto(news);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, newsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(newsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();
        news.setId(count.incrementAndGet());

        // Create the News
        NewsDTO newsDTO = newsMapper.toDto(news);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(newsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();
        news.setId(count.incrementAndGet());

        // Create the News
        NewsDTO newsDTO = newsMapper.toDto(news);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        int databaseSizeBeforeDelete = newsRepository.findAll().size();

        // Delete the news
        restNewsMockMvc
            .perform(delete(ENTITY_API_URL_ID, news.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
