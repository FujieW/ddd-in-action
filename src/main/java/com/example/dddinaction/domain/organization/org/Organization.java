package com.example.dddinaction.domain.organization.org;

import com.example.dddinaction.common.framework.domain.AuditableEntity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 组织
 */
@Data
public class Organization extends AuditableEntity {
    private Long id;
    private Long tenantId;
    private Long superiorId;
    private String orgType;
    private Long leaderId;
    private String name;
    private OrgStatus status;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime lastUpdatedAt;
    private Long lastUpdatedBy;

    public Organization(LocalDateTime createdAt, Long userId) {
        super(LocalDateTime.now(), userId);
        this.status = OrgStatus.EFFECTIVE;
    }


    public void cancel() {
        this.status = OrgStatus.TERMINATED;
    }

    public boolean isEffective() {
        return this.status == OrgStatus.EFFECTIVE;
    }
}
