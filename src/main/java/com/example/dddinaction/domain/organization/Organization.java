package com.example.dddinaction.domain.organization;

import java.time.LocalDateTime;

/**
 * 组织
 */
public class Organization {
    private Long id;
    private Long tenantId;
    private Long superiorId;
    private String orgTypeCode;
    private Long leaderId;
    private String name;
    private OrgStatus status; // 使用了枚举类型
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime lastUpdatedAt;
    private Long lastUpdatedBy;

    public Organization() {
        this.status = OrgStatus.EFFECTIVE;
    }
}
