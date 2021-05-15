package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Device;
import com.mycompany.myapp.domain.enumeration.DeviceStatus;
import com.mycompany.myapp.domain.enumeration.OS;
import com.mycompany.myapp.repository.DeviceRepository;
import com.mycompany.myapp.repository.search.DeviceSearchRepository;
import com.mycompany.myapp.service.dto.DeviceDTO;
import com.mycompany.myapp.service.mapper.DeviceMapper;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DeviceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DeviceResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BLACKLISTED = false;
    private static final Boolean UPDATED_BLACKLISTED = true;

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_LINK_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LINK_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BLACKLIST_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BLACKLIST_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_BLACKLIST_REASON = "AAAAAAAAAA";
    private static final String UPDATED_BLACKLIST_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_IMEI = "AAAAAAAAAA";
    private static final String UPDATED_IMEI = "BBBBBBBBBB";

    private static final Instant DEFAULT_CAMERA_PERM_GRANTED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CAMERA_PERM_GRANTED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LOCATION_PERM_GRANTED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOCATION_PERM_GRANTED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BG_INTERNET_PERM_GRANTED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BG_INTERNET_PERM_GRANTED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final DeviceStatus DEFAULT_STATUS = DeviceStatus.BLACKLISTED;
    private static final DeviceStatus UPDATED_STATUS = DeviceStatus.UNVERIFIED;

    private static final OS DEFAULT_OS = OS.ANDROID_OS;
    private static final OS UPDATED_OS = OS.IOS;

    private static final String ENTITY_API_URL = "/api/devices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/devices";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceMapper deviceMapper;

    /**
     * This repository is mocked in the com.mycompany.myapp.repository.search test package.
     *
     * @see com.mycompany.myapp.repository.search.DeviceSearchRepositoryMockConfiguration
     */
    @Autowired
    private DeviceSearchRepository mockDeviceSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeviceMockMvc;

    private Device device;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Device createEntity(EntityManager em) {
        Device device = new Device()
            .type(DEFAULT_TYPE)
            .blacklisted(DEFAULT_BLACKLISTED)
            .hash(DEFAULT_HASH)
            .name(DEFAULT_NAME)
            .linkDate(DEFAULT_LINK_DATE)
            .blacklistDate(DEFAULT_BLACKLIST_DATE)
            .blacklistReason(DEFAULT_BLACKLIST_REASON)
            .imei(DEFAULT_IMEI)
            .cameraPermGranted(DEFAULT_CAMERA_PERM_GRANTED)
            .locationPermGranted(DEFAULT_LOCATION_PERM_GRANTED)
            .bgInternetPermGranted(DEFAULT_BG_INTERNET_PERM_GRANTED)
            .status(DEFAULT_STATUS)
            .os(DEFAULT_OS);
        return device;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Device createUpdatedEntity(EntityManager em) {
        Device device = new Device()
            .type(UPDATED_TYPE)
            .blacklisted(UPDATED_BLACKLISTED)
            .hash(UPDATED_HASH)
            .name(UPDATED_NAME)
            .linkDate(UPDATED_LINK_DATE)
            .blacklistDate(UPDATED_BLACKLIST_DATE)
            .blacklistReason(UPDATED_BLACKLIST_REASON)
            .imei(UPDATED_IMEI)
            .cameraPermGranted(UPDATED_CAMERA_PERM_GRANTED)
            .locationPermGranted(UPDATED_LOCATION_PERM_GRANTED)
            .bgInternetPermGranted(UPDATED_BG_INTERNET_PERM_GRANTED)
            .status(UPDATED_STATUS)
            .os(UPDATED_OS);
        return device;
    }

    @BeforeEach
    public void initTest() {
        device = createEntity(em);
    }

    @Test
    @Transactional
    void createDevice() throws Exception {
        int databaseSizeBeforeCreate = deviceRepository.findAll().size();
        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);
        restDeviceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeCreate + 1);
        Device testDevice = deviceList.get(deviceList.size() - 1);
        assertThat(testDevice.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDevice.getBlacklisted()).isEqualTo(DEFAULT_BLACKLISTED);
        assertThat(testDevice.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testDevice.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDevice.getLinkDate()).isEqualTo(DEFAULT_LINK_DATE);
        assertThat(testDevice.getBlacklistDate()).isEqualTo(DEFAULT_BLACKLIST_DATE);
        assertThat(testDevice.getBlacklistReason()).isEqualTo(DEFAULT_BLACKLIST_REASON);
        assertThat(testDevice.getImei()).isEqualTo(DEFAULT_IMEI);
        assertThat(testDevice.getCameraPermGranted()).isEqualTo(DEFAULT_CAMERA_PERM_GRANTED);
        assertThat(testDevice.getLocationPermGranted()).isEqualTo(DEFAULT_LOCATION_PERM_GRANTED);
        assertThat(testDevice.getBgInternetPermGranted()).isEqualTo(DEFAULT_BG_INTERNET_PERM_GRANTED);
        assertThat(testDevice.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDevice.getOs()).isEqualTo(DEFAULT_OS);

        // Validate the Device in Elasticsearch
        verify(mockDeviceSearchRepository, times(1)).save(testDevice);
    }

    @Test
    @Transactional
    void createDeviceWithExistingId() throws Exception {
        // Create the Device with an existing ID
        device.setId(1L);
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        int databaseSizeBeforeCreate = deviceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeCreate);

        // Validate the Device in Elasticsearch
        verify(mockDeviceSearchRepository, times(0)).save(device);
    }

    @Test
    @Transactional
    void getAllDevices() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList
        restDeviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(device.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].blacklisted").value(hasItem(DEFAULT_BLACKLISTED.booleanValue())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].linkDate").value(hasItem(DEFAULT_LINK_DATE.toString())))
            .andExpect(jsonPath("$.[*].blacklistDate").value(hasItem(DEFAULT_BLACKLIST_DATE.toString())))
            .andExpect(jsonPath("$.[*].blacklistReason").value(hasItem(DEFAULT_BLACKLIST_REASON)))
            .andExpect(jsonPath("$.[*].imei").value(hasItem(DEFAULT_IMEI)))
            .andExpect(jsonPath("$.[*].cameraPermGranted").value(hasItem(DEFAULT_CAMERA_PERM_GRANTED.toString())))
            .andExpect(jsonPath("$.[*].locationPermGranted").value(hasItem(DEFAULT_LOCATION_PERM_GRANTED.toString())))
            .andExpect(jsonPath("$.[*].bgInternetPermGranted").value(hasItem(DEFAULT_BG_INTERNET_PERM_GRANTED.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].os").value(hasItem(DEFAULT_OS.toString())));
    }

    @Test
    @Transactional
    void getDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get the device
        restDeviceMockMvc
            .perform(get(ENTITY_API_URL_ID, device.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(device.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.blacklisted").value(DEFAULT_BLACKLISTED.booleanValue()))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.linkDate").value(DEFAULT_LINK_DATE.toString()))
            .andExpect(jsonPath("$.blacklistDate").value(DEFAULT_BLACKLIST_DATE.toString()))
            .andExpect(jsonPath("$.blacklistReason").value(DEFAULT_BLACKLIST_REASON))
            .andExpect(jsonPath("$.imei").value(DEFAULT_IMEI))
            .andExpect(jsonPath("$.cameraPermGranted").value(DEFAULT_CAMERA_PERM_GRANTED.toString()))
            .andExpect(jsonPath("$.locationPermGranted").value(DEFAULT_LOCATION_PERM_GRANTED.toString()))
            .andExpect(jsonPath("$.bgInternetPermGranted").value(DEFAULT_BG_INTERNET_PERM_GRANTED.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.os").value(DEFAULT_OS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDevice() throws Exception {
        // Get the device
        restDeviceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();

        // Update the device
        Device updatedDevice = deviceRepository.findById(device.getId()).get();
        // Disconnect from session so that the updates on updatedDevice are not directly saved in db
        em.detach(updatedDevice);
        updatedDevice
            .type(UPDATED_TYPE)
            .blacklisted(UPDATED_BLACKLISTED)
            .hash(UPDATED_HASH)
            .name(UPDATED_NAME)
            .linkDate(UPDATED_LINK_DATE)
            .blacklistDate(UPDATED_BLACKLIST_DATE)
            .blacklistReason(UPDATED_BLACKLIST_REASON)
            .imei(UPDATED_IMEI)
            .cameraPermGranted(UPDATED_CAMERA_PERM_GRANTED)
            .locationPermGranted(UPDATED_LOCATION_PERM_GRANTED)
            .bgInternetPermGranted(UPDATED_BG_INTERNET_PERM_GRANTED)
            .status(UPDATED_STATUS)
            .os(UPDATED_OS);
        DeviceDTO deviceDTO = deviceMapper.toDto(updatedDevice);

        restDeviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deviceDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
        Device testDevice = deviceList.get(deviceList.size() - 1);
        assertThat(testDevice.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDevice.getBlacklisted()).isEqualTo(UPDATED_BLACKLISTED);
        assertThat(testDevice.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testDevice.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDevice.getLinkDate()).isEqualTo(UPDATED_LINK_DATE);
        assertThat(testDevice.getBlacklistDate()).isEqualTo(UPDATED_BLACKLIST_DATE);
        assertThat(testDevice.getBlacklistReason()).isEqualTo(UPDATED_BLACKLIST_REASON);
        assertThat(testDevice.getImei()).isEqualTo(UPDATED_IMEI);
        assertThat(testDevice.getCameraPermGranted()).isEqualTo(UPDATED_CAMERA_PERM_GRANTED);
        assertThat(testDevice.getLocationPermGranted()).isEqualTo(UPDATED_LOCATION_PERM_GRANTED);
        assertThat(testDevice.getBgInternetPermGranted()).isEqualTo(UPDATED_BG_INTERNET_PERM_GRANTED);
        assertThat(testDevice.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDevice.getOs()).isEqualTo(UPDATED_OS);

        // Validate the Device in Elasticsearch
        verify(mockDeviceSearchRepository).save(testDevice);
    }

    @Test
    @Transactional
    void putNonExistingDevice() throws Exception {
        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();
        device.setId(count.incrementAndGet());

        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deviceDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Device in Elasticsearch
        verify(mockDeviceSearchRepository, times(0)).save(device);
    }

    @Test
    @Transactional
    void putWithIdMismatchDevice() throws Exception {
        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();
        device.setId(count.incrementAndGet());

        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Device in Elasticsearch
        verify(mockDeviceSearchRepository, times(0)).save(device);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDevice() throws Exception {
        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();
        device.setId(count.incrementAndGet());

        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Device in Elasticsearch
        verify(mockDeviceSearchRepository, times(0)).save(device);
    }

    @Test
    @Transactional
    void partialUpdateDeviceWithPatch() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();

        // Update the device using partial update
        Device partialUpdatedDevice = new Device();
        partialUpdatedDevice.setId(device.getId());

        partialUpdatedDevice
            .hash(UPDATED_HASH)
            .blacklistDate(UPDATED_BLACKLIST_DATE)
            .blacklistReason(UPDATED_BLACKLIST_REASON)
            .imei(UPDATED_IMEI)
            .bgInternetPermGranted(UPDATED_BG_INTERNET_PERM_GRANTED)
            .os(UPDATED_OS);

        restDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDevice.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDevice))
            )
            .andExpect(status().isOk());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
        Device testDevice = deviceList.get(deviceList.size() - 1);
        assertThat(testDevice.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDevice.getBlacklisted()).isEqualTo(DEFAULT_BLACKLISTED);
        assertThat(testDevice.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testDevice.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDevice.getLinkDate()).isEqualTo(DEFAULT_LINK_DATE);
        assertThat(testDevice.getBlacklistDate()).isEqualTo(UPDATED_BLACKLIST_DATE);
        assertThat(testDevice.getBlacklistReason()).isEqualTo(UPDATED_BLACKLIST_REASON);
        assertThat(testDevice.getImei()).isEqualTo(UPDATED_IMEI);
        assertThat(testDevice.getCameraPermGranted()).isEqualTo(DEFAULT_CAMERA_PERM_GRANTED);
        assertThat(testDevice.getLocationPermGranted()).isEqualTo(DEFAULT_LOCATION_PERM_GRANTED);
        assertThat(testDevice.getBgInternetPermGranted()).isEqualTo(UPDATED_BG_INTERNET_PERM_GRANTED);
        assertThat(testDevice.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDevice.getOs()).isEqualTo(UPDATED_OS);
    }

    @Test
    @Transactional
    void fullUpdateDeviceWithPatch() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();

        // Update the device using partial update
        Device partialUpdatedDevice = new Device();
        partialUpdatedDevice.setId(device.getId());

        partialUpdatedDevice
            .type(UPDATED_TYPE)
            .blacklisted(UPDATED_BLACKLISTED)
            .hash(UPDATED_HASH)
            .name(UPDATED_NAME)
            .linkDate(UPDATED_LINK_DATE)
            .blacklistDate(UPDATED_BLACKLIST_DATE)
            .blacklistReason(UPDATED_BLACKLIST_REASON)
            .imei(UPDATED_IMEI)
            .cameraPermGranted(UPDATED_CAMERA_PERM_GRANTED)
            .locationPermGranted(UPDATED_LOCATION_PERM_GRANTED)
            .bgInternetPermGranted(UPDATED_BG_INTERNET_PERM_GRANTED)
            .status(UPDATED_STATUS)
            .os(UPDATED_OS);

        restDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDevice.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDevice))
            )
            .andExpect(status().isOk());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
        Device testDevice = deviceList.get(deviceList.size() - 1);
        assertThat(testDevice.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDevice.getBlacklisted()).isEqualTo(UPDATED_BLACKLISTED);
        assertThat(testDevice.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testDevice.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDevice.getLinkDate()).isEqualTo(UPDATED_LINK_DATE);
        assertThat(testDevice.getBlacklistDate()).isEqualTo(UPDATED_BLACKLIST_DATE);
        assertThat(testDevice.getBlacklistReason()).isEqualTo(UPDATED_BLACKLIST_REASON);
        assertThat(testDevice.getImei()).isEqualTo(UPDATED_IMEI);
        assertThat(testDevice.getCameraPermGranted()).isEqualTo(UPDATED_CAMERA_PERM_GRANTED);
        assertThat(testDevice.getLocationPermGranted()).isEqualTo(UPDATED_LOCATION_PERM_GRANTED);
        assertThat(testDevice.getBgInternetPermGranted()).isEqualTo(UPDATED_BG_INTERNET_PERM_GRANTED);
        assertThat(testDevice.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDevice.getOs()).isEqualTo(UPDATED_OS);
    }

    @Test
    @Transactional
    void patchNonExistingDevice() throws Exception {
        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();
        device.setId(count.incrementAndGet());

        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deviceDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Device in Elasticsearch
        verify(mockDeviceSearchRepository, times(0)).save(device);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDevice() throws Exception {
        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();
        device.setId(count.incrementAndGet());

        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Device in Elasticsearch
        verify(mockDeviceSearchRepository, times(0)).save(device);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDevice() throws Exception {
        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();
        device.setId(count.incrementAndGet());

        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deviceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Device in Elasticsearch
        verify(mockDeviceSearchRepository, times(0)).save(device);
    }

    @Test
    @Transactional
    void deleteDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        int databaseSizeBeforeDelete = deviceRepository.findAll().size();

        // Delete the device
        restDeviceMockMvc
            .perform(delete(ENTITY_API_URL_ID, device.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Device in Elasticsearch
        verify(mockDeviceSearchRepository, times(1)).deleteById(device.getId());
    }

    @Test
    @Transactional
    void searchDevice() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        deviceRepository.saveAndFlush(device);
        when(mockDeviceSearchRepository.search(queryStringQuery("id:" + device.getId()))).thenReturn(Collections.singletonList(device));

        // Search the device
        restDeviceMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + device.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(device.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].blacklisted").value(hasItem(DEFAULT_BLACKLISTED.booleanValue())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].linkDate").value(hasItem(DEFAULT_LINK_DATE.toString())))
            .andExpect(jsonPath("$.[*].blacklistDate").value(hasItem(DEFAULT_BLACKLIST_DATE.toString())))
            .andExpect(jsonPath("$.[*].blacklistReason").value(hasItem(DEFAULT_BLACKLIST_REASON)))
            .andExpect(jsonPath("$.[*].imei").value(hasItem(DEFAULT_IMEI)))
            .andExpect(jsonPath("$.[*].cameraPermGranted").value(hasItem(DEFAULT_CAMERA_PERM_GRANTED.toString())))
            .andExpect(jsonPath("$.[*].locationPermGranted").value(hasItem(DEFAULT_LOCATION_PERM_GRANTED.toString())))
            .andExpect(jsonPath("$.[*].bgInternetPermGranted").value(hasItem(DEFAULT_BG_INTERNET_PERM_GRANTED.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].os").value(hasItem(DEFAULT_OS.toString())));
    }
}
