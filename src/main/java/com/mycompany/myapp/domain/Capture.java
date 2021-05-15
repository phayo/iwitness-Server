package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.CaptureMode;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Capture.
 */
@Entity
@Table(name = "capture")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "capture")
public class Capture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "duration")
    private Duration duration;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "cloud_url")
    private String cloudUrl;

    @Column(name = "record_start_time")
    private Instant recordStartTime;

    @Column(name = "record_end_time")
    private Instant recordEndTime;

    @Column(name = "cloud_upload_start_time")
    private Instant cloudUploadStartTime;

    @Column(name = "cloud_uploadpload_end_time")
    private Instant cloudUploadploadEndTime;

    @Column(name = "server_upload_time")
    private Instant serverUploadTime;

    @Column(name = "public_hash")
    private String publicHash;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "hash")
    private String hash;

    @Enumerated(EnumType.STRING)
    @Column(name = "capture_mode")
    private CaptureMode captureMode;

    @OneToMany(mappedBy = "capture")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "capture" }, allowSetters = true)
    private Set<Device> devices = new HashSet<>();

    @OneToMany(mappedBy = "capture")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "capture" }, allowSetters = true)
    private Set<Tag> tags = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Capture id(Long id) {
        this.id = id;
        return this;
    }

    public Duration getDuration() {
        return this.duration;
    }

    public Capture duration(Duration duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return this.title;
    }

    public Capture title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Capture description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCloudUrl() {
        return this.cloudUrl;
    }

    public Capture cloudUrl(String cloudUrl) {
        this.cloudUrl = cloudUrl;
        return this;
    }

    public void setCloudUrl(String cloudUrl) {
        this.cloudUrl = cloudUrl;
    }

    public Instant getRecordStartTime() {
        return this.recordStartTime;
    }

    public Capture recordStartTime(Instant recordStartTime) {
        this.recordStartTime = recordStartTime;
        return this;
    }

    public void setRecordStartTime(Instant recordStartTime) {
        this.recordStartTime = recordStartTime;
    }

    public Instant getRecordEndTime() {
        return this.recordEndTime;
    }

    public Capture recordEndTime(Instant recordEndTime) {
        this.recordEndTime = recordEndTime;
        return this;
    }

    public void setRecordEndTime(Instant recordEndTime) {
        this.recordEndTime = recordEndTime;
    }

    public Instant getCloudUploadStartTime() {
        return this.cloudUploadStartTime;
    }

    public Capture cloudUploadStartTime(Instant cloudUploadStartTime) {
        this.cloudUploadStartTime = cloudUploadStartTime;
        return this;
    }

    public void setCloudUploadStartTime(Instant cloudUploadStartTime) {
        this.cloudUploadStartTime = cloudUploadStartTime;
    }

    public Instant getCloudUploadploadEndTime() {
        return this.cloudUploadploadEndTime;
    }

    public Capture cloudUploadploadEndTime(Instant cloudUploadploadEndTime) {
        this.cloudUploadploadEndTime = cloudUploadploadEndTime;
        return this;
    }

    public void setCloudUploadploadEndTime(Instant cloudUploadploadEndTime) {
        this.cloudUploadploadEndTime = cloudUploadploadEndTime;
    }

    public Instant getServerUploadTime() {
        return this.serverUploadTime;
    }

    public Capture serverUploadTime(Instant serverUploadTime) {
        this.serverUploadTime = serverUploadTime;
        return this;
    }

    public void setServerUploadTime(Instant serverUploadTime) {
        this.serverUploadTime = serverUploadTime;
    }

    public String getPublicHash() {
        return this.publicHash;
    }

    public Capture publicHash(String publicHash) {
        this.publicHash = publicHash;
        return this;
    }

    public void setPublicHash(String publicHash) {
        this.publicHash = publicHash;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Capture latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Capture longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getHash() {
        return this.hash;
    }

    public Capture hash(String hash) {
        this.hash = hash;
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public CaptureMode getCaptureMode() {
        return this.captureMode;
    }

    public Capture captureMode(CaptureMode captureMode) {
        this.captureMode = captureMode;
        return this;
    }

    public void setCaptureMode(CaptureMode captureMode) {
        this.captureMode = captureMode;
    }

    public Set<Device> getDevices() {
        return this.devices;
    }

    public Capture devices(Set<Device> devices) {
        this.setDevices(devices);
        return this;
    }

    public Capture addDevice(Device device) {
        this.devices.add(device);
        device.setCapture(this);
        return this;
    }

    public Capture removeDevice(Device device) {
        this.devices.remove(device);
        device.setCapture(null);
        return this;
    }

    public void setDevices(Set<Device> devices) {
        if (this.devices != null) {
            this.devices.forEach(i -> i.setCapture(null));
        }
        if (devices != null) {
            devices.forEach(i -> i.setCapture(this));
        }
        this.devices = devices;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public Capture tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public Capture addTags(Tag tag) {
        this.tags.add(tag);
        tag.setCapture(this);
        return this;
    }

    public Capture removeTags(Tag tag) {
        this.tags.remove(tag);
        tag.setCapture(null);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        if (this.tags != null) {
            this.tags.forEach(i -> i.setCapture(null));
        }
        if (tags != null) {
            tags.forEach(i -> i.setCapture(this));
        }
        this.tags = tags;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Capture)) {
            return false;
        }
        return id != null && id.equals(((Capture) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Capture{" +
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
