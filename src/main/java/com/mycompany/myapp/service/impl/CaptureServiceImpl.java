package com.mycompany.myapp.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mycompany.myapp.domain.Capture;
import com.mycompany.myapp.repository.CaptureRepository;
import com.mycompany.myapp.repository.search.CaptureSearchRepository;
import com.mycompany.myapp.service.CaptureService;
import com.mycompany.myapp.service.dto.CaptureDTO;
import com.mycompany.myapp.service.mapper.CaptureMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Capture}.
 */
@Service
@Transactional
public class CaptureServiceImpl implements CaptureService {

    private final Logger log = LoggerFactory.getLogger(CaptureServiceImpl.class);

    private final CaptureRepository captureRepository;

    private final CaptureMapper captureMapper;

    private final CaptureSearchRepository captureSearchRepository;

    public CaptureServiceImpl(
        CaptureRepository captureRepository,
        CaptureMapper captureMapper,
        CaptureSearchRepository captureSearchRepository
    ) {
        this.captureRepository = captureRepository;
        this.captureMapper = captureMapper;
        this.captureSearchRepository = captureSearchRepository;
    }

    @Override
    public CaptureDTO save(CaptureDTO captureDTO) {
        log.debug("Request to save Capture : {}", captureDTO);
        Capture capture = captureMapper.toEntity(captureDTO);
        capture = captureRepository.save(capture);
        CaptureDTO result = captureMapper.toDto(capture);
        captureSearchRepository.save(capture);
        return result;
    }

    @Override
    public Optional<CaptureDTO> partialUpdate(CaptureDTO captureDTO) {
        log.debug("Request to partially update Capture : {}", captureDTO);

        return captureRepository
            .findById(captureDTO.getId())
            .map(
                existingCapture -> {
                    captureMapper.partialUpdate(existingCapture, captureDTO);
                    return existingCapture;
                }
            )
            .map(captureRepository::save)
            .map(
                savedCapture -> {
                    captureSearchRepository.save(savedCapture);

                    return savedCapture;
                }
            )
            .map(captureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CaptureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Captures");
        return captureRepository.findAll(pageable).map(captureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CaptureDTO> findOne(Long id) {
        log.debug("Request to get Capture : {}", id);
        return captureRepository.findById(id).map(captureMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Capture : {}", id);
        captureRepository.deleteById(id);
        captureSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CaptureDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Captures for query {}", query);
        return captureSearchRepository.search(queryStringQuery(query), pageable).map(captureMapper::toDto);
    }
}
