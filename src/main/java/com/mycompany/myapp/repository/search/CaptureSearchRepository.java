package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Capture;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Capture} entity.
 */
public interface CaptureSearchRepository extends ElasticsearchRepository<Capture, Long> {}
