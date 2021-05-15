package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CaptureDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Capture}.
 */
public interface CaptureService {
    /**
     * Save a capture.
     *
     * @param captureDTO the entity to save.
     * @return the persisted entity.
     */
    CaptureDTO save(CaptureDTO captureDTO);

    /**
     * Partially updates a capture.
     *
     * @param captureDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CaptureDTO> partialUpdate(CaptureDTO captureDTO);

    /**
     * Get all the captures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CaptureDTO> findAll(Pageable pageable);

    /**
     * Get the "id" capture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CaptureDTO> findOne(Long id);

    /**
     * Delete the "id" capture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the capture corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CaptureDTO> search(String query, Pageable pageable);
}
