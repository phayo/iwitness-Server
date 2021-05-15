package com.mycompany.myapp.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.mycompany.myapp.repository.CaptureRepository;
import com.mycompany.myapp.service.CaptureService;
import com.mycompany.myapp.service.dto.CaptureDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Capture}.
 */
@RestController
@RequestMapping("/api")
public class CaptureResource {

    private final Logger log = LoggerFactory.getLogger(CaptureResource.class);

    private static final String ENTITY_NAME = "capture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CaptureService captureService;

    private final CaptureRepository captureRepository;

    public CaptureResource(CaptureService captureService, CaptureRepository captureRepository) {
        this.captureService = captureService;
        this.captureRepository = captureRepository;
    }

    /**
     * {@code POST  /captures} : Create a new capture.
     *
     * @param captureDTO the captureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new captureDTO, or with status {@code 400 (Bad Request)} if the capture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/captures")
    public ResponseEntity<CaptureDTO> createCapture(@RequestBody CaptureDTO captureDTO) throws URISyntaxException {
        log.debug("REST request to save Capture : {}", captureDTO);
        if (captureDTO.getId() != null) {
            throw new BadRequestAlertException("A new capture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CaptureDTO result = captureService.save(captureDTO);
        return ResponseEntity
            .created(new URI("/api/captures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /captures/:id} : Updates an existing capture.
     *
     * @param id the id of the captureDTO to save.
     * @param captureDTO the captureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated captureDTO,
     * or with status {@code 400 (Bad Request)} if the captureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the captureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/captures/{id}")
    public ResponseEntity<CaptureDTO> updateCapture(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CaptureDTO captureDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Capture : {}, {}", id, captureDTO);
        if (captureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, captureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!captureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CaptureDTO result = captureService.save(captureDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, captureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /captures/:id} : Partial updates given fields of an existing capture, field will ignore if it is null
     *
     * @param id the id of the captureDTO to save.
     * @param captureDTO the captureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated captureDTO,
     * or with status {@code 400 (Bad Request)} if the captureDTO is not valid,
     * or with status {@code 404 (Not Found)} if the captureDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the captureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/captures/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CaptureDTO> partialUpdateCapture(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CaptureDTO captureDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Capture partially : {}, {}", id, captureDTO);
        if (captureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, captureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!captureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CaptureDTO> result = captureService.partialUpdate(captureDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, captureDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /captures} : get all the captures.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of captures in body.
     */
    @GetMapping("/captures")
    public ResponseEntity<List<CaptureDTO>> getAllCaptures(Pageable pageable) {
        log.debug("REST request to get a page of Captures");
        Page<CaptureDTO> page = captureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /captures/:id} : get the "id" capture.
     *
     * @param id the id of the captureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the captureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/captures/{id}")
    public ResponseEntity<CaptureDTO> getCapture(@PathVariable Long id) {
        log.debug("REST request to get Capture : {}", id);
        Optional<CaptureDTO> captureDTO = captureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(captureDTO);
    }

    /**
     * {@code DELETE  /captures/:id} : delete the "id" capture.
     *
     * @param id the id of the captureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/captures/{id}")
    public ResponseEntity<Void> deleteCapture(@PathVariable Long id) {
        log.debug("REST request to delete Capture : {}", id);
        captureService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/captures?query=:query} : search for the capture corresponding
     * to the query.
     *
     * @param query the query of the capture search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/captures")
    public ResponseEntity<List<CaptureDTO>> searchCaptures(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Captures for query {}", query);
        Page<CaptureDTO> page = captureService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
