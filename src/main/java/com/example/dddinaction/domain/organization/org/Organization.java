package com.example.dddinaction.domain.organization.org;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 组织
 */
@Data
public class Organization {
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

    public Organization() {
        this.status = OrgStatus.EFFECTIVE;
    }


    public void cancel() {
        this.status = OrgStatus.TERMINATED;
    }

    public boolean isEffective() {
        return this.status == OrgStatus.EFFECTIVE;
    }
}
