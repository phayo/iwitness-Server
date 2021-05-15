package com.mycompany.myapp.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CaptureSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CaptureSearchRepositoryMockConfiguration {

    @MockBean
    private CaptureSearchRepository mockCaptureSearchRepository;
}
