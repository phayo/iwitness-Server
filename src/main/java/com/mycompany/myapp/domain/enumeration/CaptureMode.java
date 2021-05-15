package com.mycompany.myapp.domain.enumeration;

/**
 * The CaptureMode enumeration.
 */
public enum CaptureMode {
    DIRECT("direct"),
    INDIRECT("indirect"),
    PANIC("panic");

    private final String value;

    CaptureMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
