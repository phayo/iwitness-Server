package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CaptureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Capture.class);
        Capture capture1 = new Capture();
        capture1.setId(1L);
        Capture capture2 = new Capture();
        capture2.setId(capture1.getId());
        assertThat(capture1).isEqualTo(capture2);
        capture2.setId(2L);
        assertThat(capture1).isNotEqualTo(capture2);
        capture1.setId(null);
        assertThat(capture1).isNotEqualTo(capture2);
    }
}
