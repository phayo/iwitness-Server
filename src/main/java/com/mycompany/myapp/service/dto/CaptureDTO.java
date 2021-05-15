package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.CaptureMode;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Capture} entity.
 */
public class CaptureDTO implements Serializable {

    private Long id;

    private Duration duration;

    private String title;

    private String description;

    private String cloudUrl;

    private Instant recordStartTime;

    private Instant recordEndTime;

    private Instant cloudUploadStartTime;

    private Instant cloudUploadploadEndTime;

    private Instant serverUploadTime;

    private String publicHash;

    private Double latitude;

    private Double longitude;

    private String hash;

    private CaptureMode captureMode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCloudUrl() {
        return cloudUrl;
    }

    public void setCloudUrl(String cloudUrl) {
        this.cloudUrl = cloudUrl;
    }

    public Instant getRecordStartTime() {
        return recordStartTime;
    }

    public void setRecordStartTime(Instant recordStartTime) {
        this.recordStartTime = recordStartTime;
    }

    public Instant getRecordEndTime() {
        return recordEndTime;
    }

    public void setRecordEndTime(Instant recordEndTime) {
        this.recordEndTime = recordEndTime;
    }

    public Instant getCloudUploadStartTime() {
        return cloudUploadStartTime;
    }

    public void setCloudUploadStartTime(Instant cloudUploadStartTime) {
        this.cloudUploadStartTime = cloudUploadStartTime;
    }

    public Instant getCloudUploadploadEndTime() {
        return cloudUploadploadEndTime;
    }

    public void setCloudUploadploadEndTime(Instant cloudUploadploadEndTime) {
        this.cloudUploadploadEndTime = cloudUploadploadEndTime;
    }

    public Instant getServerUploadTime() {
        return serverUploadTime;
    }

    public void setServerUploadTime(Instant serverUploadTime) {
        this.serverUploadTime = serverUploadTime;
    }

    public String getPublicHash() {
        return publicHash;
    }

    public void setPublicHash(String publicHash) {
        this.publicHash = publicHash;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public CaptureMode getCaptureMode() {
        return captureMode;
    }

    public void setCaptureMode(CaptureMode captureMode) {
        this.captureMode = captureMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CaptureDTO)) {
            return false;
        }

        CaptureDTO captureDTO = (CaptureDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, captureDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CaptureDTO{" +
            "id=" + getId() +
            ", duration='" + getDuration() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", cloudUrl='" + getCloudUrl() + "'" +
            ", recordStartTime='" + getRecordStartTime() + "'" +
            ", recordEndTime='" + getRecordEndTime() + "'" +
            ", cloudUploadStartTime='" + getCloudUploadStartTime() + "'" +
            ", cloudUploadploadEndTime='" + getCloudUploadploadEndTime() + "'" +
            ", serverUploadTime='" + getServerUploadTime() + "'" +
            ", publicHash='" + getPublicHash() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", hash='" + getHash() + "'" +
            ", captureMode='" + getCaptureMode() + "'" +
            "}";
    }
}
