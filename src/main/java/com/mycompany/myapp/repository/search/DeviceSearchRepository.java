package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Device;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Device} entity.
 */
public interface DeviceSearchRepository extends ElasticsearchRepository<Device, Long> {}
