package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.DeviceStatus;
import com.mycompany.myapp.domain.enumeration.OS;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Device} entity.
 */
public class DeviceDTO implements Serializable {

    private Long id;

    private String type;

    private Boolean blacklisted;

    private String hash;

    private String name;

    private Instant linkDate;

    private Instant blacklistDate;

    private String blacklistReason;

    private String imei;

    private Instant cameraPermGranted;

    private Instant locationPermGranted;

    private Instant bgInternetPermGranted;

    private DeviceStatus status;

    private OS os;

    private CaptureDTO capture;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getBlacklisted() {
        return blacklisted;
    }

    public void setBlacklisted(Boolean blacklisted) {
        this.blacklisted = blacklisted;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getLinkDate() {
        return linkDate;
    }

    public void setLinkDate(Instant linkDate) {
        this.linkDate = linkDate;
    }

    public Instant getBlacklistDate() {
        return blacklistDate;
    }

    public void setBlacklistDate(Instant blacklistDate) {
        this.blacklistDate = blacklistDate;
    }

    public String getBlacklistReason() {
        return blacklistReason;
    }

    public void setBlacklistReason(String blacklistReason) {
        this.blacklistReason = blacklistReason;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Instant getCameraPermGranted() {
        return cameraPermGranted;
    }

    public void setCameraPermGranted(Instant cameraPermGranted) {
        this.cameraPermGranted = cameraPermGranted;
    }

    public Instant getLocationPermGranted() {
        return locationPermGranted;
    }

    public void setLocationPermGranted(Instant locationPermGranted) {
        this.locationPermGranted = locationPermGranted;
    }

    public Instant getBgInternetPermGranted() {
        return bgInternetPermGranted;
    }

    public void setBgInternetPermGranted(Instant bgInternetPermGranted) {
        this.bgInternetPermGranted = bgInternetPermGranted;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }

    public OS getOs() {
        return os;
    }

    public void setOs(OS os) {
        this.os = os;
    }

    public CaptureDTO getCapture() {
        return capture;
    }

    public void setCapture(CaptureDTO capture) {
        this.capture = capture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceDTO)) {
            return false;
        }

        DeviceDTO deviceDTO = (DeviceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, deviceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", blacklisted='" + getBlacklisted() + "'" +
            ", hash='" + getHash() + "'" +
            ", name='" + getName() + "'" +
            ", linkDate='" + getLinkDate() + "'" +
            ", blacklistDate='" + getBlacklistDate() + "'" +
            ", blacklistReason='" + getBlacklistReason() + "'" +
            ", imei='" + getImei() + "'" +
            ", cameraPermGranted='" + getCameraPermGranted() + "'" +
            ", locationPermGranted='" + getLocationPermGranted() + "'" +
            ", bgInternetPermGranted='" + getBgInternetPermGranted() + "'" +
            ", status='" + getStatus() + "'" +
            ", os='" + getOs() + "'" +
            ", capture=" + getCapture() +
            "}";
    }
}
