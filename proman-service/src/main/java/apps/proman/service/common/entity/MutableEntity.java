/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: MutableEntity.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package apps.proman.service.common.entity;

import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * Base class for all mutable entities to inherit the default behavior.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class MutableEntity implements Entity {

    @Version
    @Column(name = "VERSION", length = 19, nullable = false)
    private Long version;

    @Column(name = "CREATED_BY")
    @NotNull
    private String createdBy;

    @Column(name = "CREATED_AT")
    @NotNull
    private ZonedDateTime createdAt;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    @Column(name = "MODIFIED_AT")
    private ZonedDateTime modifiedAt;

    @PrePersist
    public void prePersist() {
        this.createdBy = "test-user";
        this.createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedBy = "test-user";
        this.modifiedAt = ZonedDateTime.now();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public ZonedDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(ZonedDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

}