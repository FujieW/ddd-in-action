package com.example.dddinaction.common.framework.domain;

import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.example.dddinaction.common.framework.domain.ChangingStatus.*;

@Data
@Setter
public class AuditableEntity {
    protected LocalDateTime createdAt;
    protected Long createdBy;
    protected LocalDateTime lastUpdatedAt;
    protected Long lastUpdatedBy;
    protected ChangingStatus changingStatus = NEW;

    public AuditableEntity() {
    }

    public AuditableEntity(LocalDateTime createdAt, Long createdBy) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public void toUpdate() {
        this.changingStatus = UPDATED;
    }

    public void toDelete() {
        this.changingStatus = DELETED;
    }

    public void toUnchanged() {
        this.changingStatus = UNCHANGED;
    }
}
