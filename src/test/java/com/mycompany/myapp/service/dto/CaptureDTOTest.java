package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CaptureDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaptureDTO.class);
        CaptureDTO captureDTO1 = new CaptureDTO();
        captureDTO1.setId(1L);
        CaptureDTO captureDTO2 = new CaptureDTO();
        assertThat(captureDTO1).isNotEqualTo(captureDTO2);
        captureDTO2.setId(captureDTO1.getId());
        assertThat(captureDTO1).isEqualTo(captureDTO2);
        captureDTO2.setId(2L);
        assertThat(captureDTO1).isNotEqualTo(captureDTO2);
        captureDTO1.setId(null);
        assertThat(captureDTO1).isNotEqualTo(captureDTO2);
    }
}
