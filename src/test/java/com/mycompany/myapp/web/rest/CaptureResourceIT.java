package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Capture;
import com.mycompany.myapp.domain.enumeration.CaptureMode;
import com.mycompany.myapp.repository.CaptureRepository;
import com.mycompany.myapp.repository.search.CaptureSearchRepository;
import com.mycompany.myapp.service.dto.CaptureDTO;
import com.mycompany.myapp.service.mapper.CaptureMapper;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CaptureResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CaptureResourceIT {

    private static final Duration DEFAULT_DURATION = Duration.ofHours(6);
    private static final Duration UPDATED_DURATION = Duration.ofHours(12);

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CLOUD_URL = "AAAAAAAAAA";
    private static final String UPDATED_CLOUD_URL = "BBBBBBBBBB";

    private static final Instant DEFAULT_RECORD_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECORD_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_RECORD_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECORD_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CLOUD_UPLOAD_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CLOUD_UPLOAD_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CLOUD_UPLOADPLOAD_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CLOUD_UPLOADPLOAD_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SERVER_UPLOAD_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SERVER_UPLOAD_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PUBLIC_HASH = "AAAAAAAAAA";
    private static final String UPDATED_PUBLIC_HASH = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final CaptureMode DEFAULT_CAPTURE_MODE = CaptureMode.DIRECT;
    private static final CaptureMode UPDATED_CAPTURE_MODE = CaptureMode.INDIRECT;

    private static final String ENTITY_API_URL = "/api/captures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/captures";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CaptureRepository captureRepository;

    @Autowired
    private CaptureMapper captureMapper;

    /**
     * This repository is mocked in the com.mycompany.myapp.repository.search test package.
     *
     * @see com.mycompany.myapp.repository.search.CaptureSearchRepositoryMockConfiguration
     */
    @Autowired
    private CaptureSearchRepository mockCaptureSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCaptureMockMvc;

    private Capture capture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Capture createEntity(EntityManager em) {
        Capture capture = new Capture()
            .duration(DEFAULT_DURATION)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .cloudUrl(DEFAULT_CLOUD_URL)
            .recordStartTime(DEFAULT_RECORD_START_TIME)
            .recordEndTime(DEFAULT_RECORD_END_TIME)
            .cloudUploadStartTime(DEFAULT_CLOUD_UPLOAD_START_TIME)
            .cloudUploadploadEndTime(DEFAULT_CLOUD_UPLOADPLOAD_END_TIME)
            .serverUploadTime(DEFAULT_SERVER_UPLOAD_TIME)
            .publicHash(DEFAULT_PUBLIC_HASH)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .hash(DEFAULT_HASH)
            .captureMode(DEFAULT_CAPTURE_MODE);
        return capture;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Capture createUpdatedEntity(EntityManager em) {
        Capture capture = new Capture()
            .duration(UPDATED_DURATION)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .cloudUrl(UPDATED_CLOUD_URL)
            .recordStartTime(UPDATED_RECORD_START_TIME)
            .recordEndTime(UPDATED_RECORD_END_TIME)
            .cloudUploadStartTime(UPDATED_CLOUD_UPLOAD_START_TIME)
            .cloudUploadploadEndTime(UPDATED_CLOUD_UPLOADPLOAD_END_TIME)
            .serverUploadTime(UPDATED_SERVER_UPLOAD_TIME)
            .publicHash(UPDATED_PUBLIC_HASH)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .hash(UPDATED_HASH)
            .captureMode(UPDATED_CAPTURE_MODE);
        return capture;
    }

    @BeforeEach
    public void initTest() {
        capture = createEntity(em);
    }

    @Test
    @Transactional
    void createCapture() throws Exception {
        int databaseSizeBeforeCreate = captureRepository.findAll().size();
        // Create the Capture
        CaptureDTO captureDTO = captureMapper.toDto(capture);
        restCaptureMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(captureDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeCreate + 1);
        Capture testCapture = captureList.get(captureList.size() - 1);
        assertThat(testCapture.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testCapture.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCapture.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCapture.getCloudUrl()).isEqualTo(DEFAULT_CLOUD_URL);
        assertThat(testCapture.getRecordStartTime()).isEqualTo(DEFAULT_RECORD_START_TIME);
        assertThat(testCapture.getRecordEndTime()).isEqualTo(DEFAULT_RECORD_END_TIME);
        assertThat(testCapture.getCloudUploadStartTime()).isEqualTo(DEFAULT_CLOUD_UPLOAD_START_TIME);
        assertThat(testCapture.getCloudUploadploadEndTime()).isEqualTo(DEFAULT_CLOUD_UPLOADPLOAD_END_TIME);
        assertThat(testCapture.getServerUploadTime()).isEqualTo(DEFAULT_SERVER_UPLOAD_TIME);
        assertThat(testCapture.getPublicHash()).isEqualTo(DEFAULT_PUBLIC_HASH);
        assertThat(testCapture.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testCapture.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testCapture.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testCapture.getCaptureMode()).isEqualTo(DEFAULT_CAPTURE_MODE);

        // Validate the Capture in Elasticsearch
        verify(mockCaptureSearchRepository, times(1)).save(testCapture);
    }

    @Test
    @Transactional
    void createCaptureWithExistingId() throws Exception {
        // Create the Capture with an existing ID
        capture.setId(1L);
        CaptureDTO captureDTO = captureMapper.toDto(capture);

        int databaseSizeBeforeCreate = captureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaptureMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(captureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeCreate);

        // Validate the Capture in Elasticsearch
        verify(mockCaptureSearchRepository, times(0)).save(capture);
    }

    @Test
    @Transactional
    void getAllCaptures() throws Exception {
        // Initialize the database
        captureRepository.saveAndFlush(capture);

        // Get all the captureList
        restCaptureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(capture.getId().intValue())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].cloudUrl").value(hasItem(DEFAULT_CLOUD_URL)))
            .andExpect(jsonPath("$.[*].recordStartTime").value(hasItem(DEFAULT_RECORD_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].recordEndTime").value(hasItem(DEFAULT_RECORD_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].cloudUploadStartTime").value(hasItem(DEFAULT_CLOUD_UPLOAD_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].cloudUploadploadEndTime").value(hasItem(DEFAULT_CLOUD_UPLOADPLOAD_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].serverUploadTime").value(hasItem(DEFAULT_SERVER_UPLOAD_TIME.toString())))
            .andExpect(jsonPath("$.[*].publicHash").value(hasItem(DEFAULT_PUBLIC_HASH)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].captureMode").value(hasItem(DEFAULT_CAPTURE_MODE.toString())));
    }

    @Test
    @Transactional
    void getCapture() throws Exception {
        // Initialize the database
        captureRepository.saveAndFlush(capture);

        // Get the capture
        restCaptureMockMvc
            .perform(get(ENTITY_API_URL_ID, capture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(capture.getId().intValue()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.cloudUrl").value(DEFAULT_CLOUD_URL))
            .andExpect(jsonPath("$.recordStartTime").value(DEFAULT_RECORD_START_TIME.toString()))
            .andExpect(jsonPath("$.recordEndTime").value(DEFAULT_RECORD_END_TIME.toString()))
            .andExpect(jsonPath("$.cloudUploadStartTime").value(DEFAULT_CLOUD_UPLOAD_START_TIME.toString()))
            .andExpect(jsonPath("$.cloudUploadploadEndTime").value(DEFAULT_CLOUD_UPLOADPLOAD_END_TIME.toString()))
            .andExpect(jsonPath("$.serverUploadTime").value(DEFAULT_SERVER_UPLOAD_TIME.toString()))
            .andExpect(jsonPath("$.publicHash").value(DEFAULT_PUBLIC_HASH))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH))
            .andExpect(jsonPath("$.captureMode").value(DEFAULT_CAPTURE_MODE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCapture() throws Exception {
        // Get the capture
        restCaptureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCapture() throws Exception {
        // Initialize the database
        captureRepository.saveAndFlush(capture);

        int databaseSizeBeforeUpdate = captureRepository.findAll().size();

        // Update the capture
        Capture updatedCapture = captureRepository.findById(capture.getId()).get();
        // Disconnect from session so that the updates on updatedCapture are not directly saved in db
        em.detach(updatedCapture);
        updatedCapture
            .duration(UPDATED_DURATION)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .cloudUrl(UPDATED_CLOUD_URL)
            .recordStartTime(UPDATED_RECORD_START_TIME)
            .recordEndTime(UPDATED_RECORD_END_TIME)
            .cloudUploadStartTime(UPDATED_CLOUD_UPLOAD_START_TIME)
            .cloudUploadploadEndTime(UPDATED_CLOUD_UPLOADPLOAD_END_TIME)
            .serverUploadTime(UPDATED_SERVER_UPLOAD_TIME)
            .publicHash(UPDATED_PUBLIC_HASH)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .hash(UPDATED_HASH)
            .captureMode(UPDATED_CAPTURE_MODE);
        CaptureDTO captureDTO = captureMapper.toDto(updatedCapture);

        restCaptureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, captureDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(captureDTO))
            )
            .andExpect(status().isOk());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeUpdate);
        Capture testCapture = captureList.get(captureList.size() - 1);
        assertThat(testCapture.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testCapture.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCapture.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCapture.getCloudUrl()).isEqualTo(UPDATED_CLOUD_URL);
        assertThat(testCapture.getRecordStartTime()).isEqualTo(UPDATED_RECORD_START_TIME);
        assertThat(testCapture.getRecordEndTime()).isEqualTo(UPDATED_RECORD_END_TIME);
        assertThat(testCapture.getCloudUploadStartTime()).isEqualTo(UPDATED_CLOUD_UPLOAD_START_TIME);
        assertThat(testCapture.getCloudUploadploadEndTime()).isEqualTo(UPDATED_CLOUD_UPLOADPLOAD_END_TIME);
        assertThat(testCapture.getServerUploadTime()).isEqualTo(UPDATED_SERVER_UPLOAD_TIME);
        assertThat(testCapture.getPublicHash()).isEqualTo(UPDATED_PUBLIC_HASH);
        assertThat(testCapture.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testCapture.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testCapture.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testCapture.getCaptureMode()).isEqualTo(UPDATED_CAPTURE_MODE);

        // Validate the Capture in Elasticsearch
        verify(mockCaptureSearchRepository).save(testCapture);
    }

    @Test
    @Transactional
    void putNonExistingCapture() throws Exception {
        int databaseSizeBeforeUpdate = captureRepository.findAll().size();
        capture.setId(count.incrementAndGet());

        // Create the Capture
        CaptureDTO captureDTO = captureMapper.toDto(capture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaptureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, captureDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(captureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Capture in Elasticsearch
        verify(mockCaptureSearchRepository, times(0)).save(capture);
    }

    @Test
    @Transactional
    void putWithIdMismatchCapture() throws Exception {
        int databaseSizeBeforeUpdate = captureRepository.findAll().size();
        capture.setId(count.incrementAndGet());

        // Create the Capture
        CaptureDTO captureDTO = captureMapper.toDto(capture);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaptureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(captureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Capture in Elasticsearch
        verify(mockCaptureSearchRepository, times(0)).save(capture);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCapture() throws Exception {
        int databaseSizeBeforeUpdate = captureRepository.findAll().size();
        capture.setId(count.incrementAndGet());

        // Create the Capture
        CaptureDTO captureDTO = captureMapper.toDto(capture);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaptureMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(captureDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Capture in Elasticsearch
        verify(mockCaptureSearchRepository, times(0)).save(capture);
    }

    @Test
    @Transactional
    void partialUpdateCaptureWithPatch() throws Exception {
        // Initialize the database
        captureRepository.saveAndFlush(capture);

        int databaseSizeBeforeUpdate = captureRepository.findAll().size();

        // Update the capture using partial update
        Capture partialUpdatedCapture = new Capture();
        partialUpdatedCapture.setId(capture.getId());

        partialUpdatedCapture
            .description(UPDATED_DESCRIPTION)
            .cloudUrl(UPDATED_CLOUD_URL)
            .recordStartTime(UPDATED_RECORD_START_TIME)
            .cloudUploadStartTime(UPDATED_CLOUD_UPLOAD_START_TIME)
            .cloudUploadploadEndTime(UPDATED_CLOUD_UPLOADPLOAD_END_TIME)
            .serverUploadTime(UPDATED_SERVER_UPLOAD_TIME)
            .longitude(UPDATED_LONGITUDE);

        restCaptureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCapture.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCapture))
            )
            .andExpect(status().isOk());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeUpdate);
        Capture testCapture = captureList.get(captureList.size() - 1);
        assertThat(testCapture.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testCapture.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCapture.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCapture.getCloudUrl()).isEqualTo(UPDATED_CLOUD_URL);
        assertThat(testCapture.getRecordStartTime()).isEqualTo(UPDATED_RECORD_START_TIME);
        assertThat(testCapture.getRecordEndTime()).isEqualTo(DEFAULT_RECORD_END_TIME);
        assertThat(testCapture.getCloudUploadStartTime()).isEqualTo(UPDATED_CLOUD_UPLOAD_START_TIME);
        assertThat(testCapture.getCloudUploadploadEndTime()).isEqualTo(UPDATED_CLOUD_UPLOADPLOAD_END_TIME);
        assertThat(testCapture.getServerUploadTime()).isEqualTo(UPDATED_SERVER_UPLOAD_TIME);
        assertThat(testCapture.getPublicHash()).isEqualTo(DEFAULT_PUBLIC_HASH);
        assertThat(testCapture.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testCapture.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testCapture.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testCapture.getCaptureMode()).isEqualTo(DEFAULT_CAPTURE_MODE);
    }

    @Test
    @Transactional
    void fullUpdateCaptureWithPatch() throws Exception {
        // Initialize the database
        captureRepository.saveAndFlush(capture);

        int databaseSizeBeforeUpdate = captureRepository.findAll().size();

        // Update the capture using partial update
        Capture partialUpdatedCapture = new Capture();
        partialUpdatedCapture.setId(capture.getId());

        partialUpdatedCapture
            .duration(UPDATED_DURATION)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .cloudUrl(UPDATED_CLOUD_URL)
            .recordStartTime(UPDATED_RECORD_START_TIME)
            .recordEndTime(UPDATED_RECORD_END_TIME)
            .cloudUploadStartTime(UPDATED_CLOUD_UPLOAD_START_TIME)
            .cloudUploadploadEndTime(UPDATED_CLOUD_UPLOADPLOAD_END_TIME)
            .serverUploadTime(UPDATED_SERVER_UPLOAD_TIME)
            .publicHash(UPDATED_PUBLIC_HASH)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .hash(UPDATED_HASH)
            .captureMode(UPDATED_CAPTURE_MODE);

        restCaptureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCapture.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCapture))
            )
            .andExpect(status().isOk());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeUpdate);
        Capture testCapture = captureList.get(captureList.size() - 1);
        assertThat(testCapture.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testCapture.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCapture.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCapture.getCloudUrl()).isEqualTo(UPDATED_CLOUD_URL);
        assertThat(testCapture.getRecordStartTime()).isEqualTo(UPDATED_RECORD_START_TIME);
        assertThat(testCapture.getRecordEndTime()).isEqualTo(UPDATED_RECORD_END_TIME);
        assertThat(testCapture.getCloudUploadStartTime()).isEqualTo(UPDATED_CLOUD_UPLOAD_START_TIME);
        assertThat(testCapture.getCloudUploadploadEndTime()).isEqualTo(UPDATED_CLOUD_UPLOADPLOAD_END_TIME);
        assertThat(testCapture.getServerUploadTime()).isEqualTo(UPDATED_SERVER_UPLOAD_TIME);
        assertThat(testCapture.getPublicHash()).isEqualTo(UPDATED_PUBLIC_HASH);
        assertThat(testCapture.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testCapture.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testCapture.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testCapture.getCaptureMode()).isEqualTo(UPDATED_CAPTURE_MODE);
    }

    @Test
    @Transactional
    void patchNonExistingCapture() throws Exception {
        int databaseSizeBeforeUpdate = captureRepository.findAll().size();
        capture.setId(count.incrementAndGet());

        // Create the Capture
        CaptureDTO captureDTO = captureMapper.toDto(capture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaptureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, captureDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(captureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Capture in Elasticsearch
        verify(mockCaptureSearchRepository, times(0)).save(capture);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCapture() throws Exception {
        int databaseSizeBeforeUpdate = captureRepository.findAll().size();
        capture.setId(count.incrementAndGet());

        // Create the Capture
        CaptureDTO captureDTO = captureMapper.toDto(capture);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaptureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(captureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Capture in Elasticsearch
        verify(mockCaptureSearchRepository, times(0)).save(capture);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCapture() throws Exception {
        int databaseSizeBeforeUpdate = captureRepository.findAll().size();
        capture.setId(count.incrementAndGet());

        // Create the Capture
        CaptureDTO captureDTO = captureMapper.toDto(capture);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaptureMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(captureDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Capture in the database
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Capture in Elasticsearch
        verify(mockCaptureSearchRepository, times(0)).save(capture);
    }

    @Test
    @Transactional
    void deleteCapture() throws Exception {
        // Initialize the database
        captureRepository.saveAndFlush(capture);

        int databaseSizeBeforeDelete = captureRepository.findAll().size();

        // Delete the capture
        restCaptureMockMvc
            .perform(delete(ENTITY_API_URL_ID, capture.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Capture> captureList = captureRepository.findAll();
        assertThat(captureList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Capture in Elasticsearch
        verify(mockCaptureSearchRepository, times(1)).deleteById(capture.getId());
    }

    @Test
    @Transactional
    void searchCapture() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        captureRepository.saveAndFlush(capture);
        when(mockCaptureSearchRepository.search(queryStringQuery("id:" + capture.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(capture), PageRequest.of(0, 1), 1));

        // Search the capture
        restCaptureMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + capture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(capture.getId().intValue())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].cloudUrl").value(hasItem(DEFAULT_CLOUD_URL)))
            .andExpect(jsonPath("$.[*].recordStartTime").value(hasItem(DEFAULT_RECORD_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].recordEndTime").value(hasItem(DEFAULT_RECORD_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].cloudUploadStartTime").value(hasItem(DEFAULT_CLOUD_UPLOAD_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].cloudUploadploadEndTime").value(hasItem(DEFAULT_CLOUD_UPLOADPLOAD_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].serverUploadTime").value(hasItem(DEFAULT_SERVER_UPLOAD_TIME.toString())))
            .andExpect(jsonPath("$.[*].publicHash").value(hasItem(DEFAULT_PUBLIC_HASH)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].captureMode").value(hasItem(DEFAULT_CAPTURE_MODE.toString())));
    }
}
