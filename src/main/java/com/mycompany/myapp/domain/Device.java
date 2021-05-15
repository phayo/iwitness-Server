package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.DeviceStatus;
import com.mycompany.myapp.domain.enumeration.OS;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Device.
 */
@Entity
@Table(name = "device")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "device")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "blacklisted")
    private Boolean blacklisted;

    @Column(name = "hash")
    private String hash;

    @Column(name = "name")
    private String name;

    @Column(name = "link_date")
    private Instant linkDate;

    @Column(name = "blacklist_date")
    private Instant blacklistDate;

    @Column(name = "blacklist_reason")
    private String blacklistReason;

    @Column(name = "imei")
    private String imei;

    @Column(name = "camera_perm_granted")
    private Instant cameraPermGranted;

    @Column(name = "location_perm_granted")
    private Instant locationPermGranted;

    @Column(name = "bg_internet_perm_granted")
    private Instant bgInternetPermGranted;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DeviceStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "os")
    private OS os;

    @ManyToOne
    @JsonIgnoreProperties(value = { "devices", "tags" }, allowSetters = true)
    private Capture capture;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Device id(Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public Device type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getBlacklisted() {
        return this.blacklisted;
    }

    public Device blacklisted(Boolean blacklisted) {
        this.blacklisted = blacklisted;
        return this;
    }

    public void setBlacklisted(Boolean blacklisted) {
        this.blacklisted = blacklisted;
    }

    public String getHash() {
        return this.hash;
    }

    public Device hash(String hash) {
        this.hash = hash;
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getName() {
        return this.name;
    }

    public Device name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getLinkDate() {
        return this.linkDate;
    }

    public Device linkDate(Instant linkDate) {
        this.linkDate = linkDate;
        return this;
    }

    public void setLinkDate(Instant linkDate) {
        this.linkDate = linkDate;
    }

    public Instant getBlacklistDate() {
        return this.blacklistDate;
    }

    public Device blacklistDate(Instant blacklistDate) {
        this.blacklistDate = blacklistDate;
        return this;
    }

    public void setBlacklistDate(Instant blacklistDate) {
        this.blacklistDate = blacklistDate;
    }

    public String getBlacklistReason() {
        return this.blacklistReason;
    }

    public Device blacklistReason(String blacklistReason) {
        this.blacklistReason = blacklistReason;
        return this;
    }

    public void setBlacklistReason(String blacklistReason) {
        this.blacklistReason = blacklistReason;
    }

    public String getImei() {
        return this.imei;
    }

    public Device imei(String imei) {
        this.imei = imei;
        return this;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Instant getCameraPermGranted() {
        return this.cameraPermGranted;
    }

    public Device cameraPermGranted(Instant cameraPermGranted) {
        this.cameraPermGranted = cameraPermGranted;
        return this;
    }

    public void setCameraPermGranted(Instant cameraPermGranted) {
        this.cameraPermGranted = cameraPermGranted;
    }

    public Instant getLocationPermGranted() {
        return this.locationPermGranted;
    }

    public Device locationPermGranted(Instant locationPermGranted) {
        this.locationPermGranted = locationPermGranted;
        return this;
    }

    public void setLocationPermGranted(Instant locationPermGranted) {
        this.locationPermGranted = locationPermGranted;
    }

    public Instant getBgInternetPermGranted() {
        return this.bgInternetPermGranted;
    }

    public Device bgInternetPermGranted(Instant bgInternetPermGranted) {
        this.bgInternetPermGranted = bgInternetPermGranted;
        return this;
    }

    public void setBgInternetPermGranted(Instant bgInternetPermGranted) {
        this.bgInternetPermGranted = bgInternetPermGranted;
    }

    public DeviceStatus getStatus() {
        return this.status;
    }

    public Device status(DeviceStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }

    public OS getOs() {
        return this.os;
    }

    public Device os(OS os) {
        this.os = os;
        return this;
    }

    public void setOs(OS os) {
        this.os = os;
    }

    public Capture getCapture() {
        return this.capture;
    }

    public Device capture(Capture capture) {
        this.setCapture(capture);
        return this;
    }

    public void setCapture(Capture capture) {
        this.capture = capture;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Device)) {
            return false;
        }
        return id != null && id.equals(((Device) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Device{" +
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
            "}";
    }
}
