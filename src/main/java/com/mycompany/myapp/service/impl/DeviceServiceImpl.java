package com.mycompany.myapp.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mycompany.myapp.domain.Device;
import com.mycompany.myapp.repository.DeviceRepository;
import com.mycompany.myapp.repository.search.DeviceSearchRepository;
import com.mycompany.myapp.service.DeviceService;
import com.mycompany.myapp.service.dto.DeviceDTO;
import com.mycompany.myapp.service.mapper.DeviceMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Device}.
 */
@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    private final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);

    private final DeviceRepository deviceRepository;

    private final DeviceMapper deviceMapper;

    private final DeviceSearchRepository deviceSearchRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository, DeviceMapper deviceMapper, DeviceSearchRepository deviceSearchRepository) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
        this.deviceSearchRepository = deviceSearchRepository;
    }

    @Override
    public DeviceDTO save(DeviceDTO deviceDTO) {
        log.debug("Request to save Device : {}", deviceDTO);
        Device device = deviceMapper.toEntity(deviceDTO);
        device = deviceRepository.save(device);
        DeviceDTO result = deviceMapper.toDto(device);
        deviceSearchRepository.save(device);
        return result;
    }

    @Override
    public Optional<DeviceDTO> partialUpdate(DeviceDTO deviceDTO) {
        log.debug("Request to partially update Device : {}", deviceDTO);

        return deviceRepository
            .findById(deviceDTO.getId())
            .map(
                existingDevice -> {
                    deviceMapper.partialUpdate(existingDevice, deviceDTO);
                    return existingDevice;
                }
            )
            .map(deviceRepository::save)
            .map(
                savedDevice -> {
                    deviceSearchRepository.save(savedDevice);

                    return savedDevice;
                }
            )
            .map(deviceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceDTO> findAll() {
        log.debug("Request to get all Devices");
        return deviceRepository.findAll().stream().map(deviceMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceDTO> findOne(Long id) {
        log.debug("Request to get Device : {}", id);
        return deviceRepository.findById(id).map(deviceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Device : {}", id);
        deviceRepository.deleteById(id);
        deviceSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceDTO> search(String query) {
        log.debug("Request to search Devices for query {}", query);
        return StreamSupport
            .stream(deviceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(deviceMapper::toDto)
            .collect(Collectors.toList());
    }
}
