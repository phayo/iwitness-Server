package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CaptureMapperTest {

    private CaptureMapper captureMapper;

    @BeforeEach
    public void setUp() {
        captureMapper = new CaptureMapperImpl();
    }
}
