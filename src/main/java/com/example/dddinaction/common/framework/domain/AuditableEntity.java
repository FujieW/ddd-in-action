package com.example.dddinaction.common.framework.domain;

import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter
public class AuditableEntity {
    protected LocalDateTime createdAt;
    protected Long createdBy;
    protected LocalDateTime lastUpdatedAt;
    protected Long lastUpdatedBy;

    public AuditableEntity(LocalDateTime createdAt, Long createdBy) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }
}
