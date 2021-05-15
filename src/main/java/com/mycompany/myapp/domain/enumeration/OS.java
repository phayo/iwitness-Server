package com.mycompany.myapp.domain.enumeration;

/**
 * The OS enumeration.
 */
public enum OS {
    ANDROID_OS("android"),
    IOS("ios");

    private final String value;

    OS(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
