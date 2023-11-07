package com.example.dddinaction.domain.organization.orgtype;

import com.example.dddinaction.domain.organization.orgtype.OrgTypeStatus;

import java.time.LocalDateTime;

/**
 * 组织类型
 */
public class OrganizationType {
    private Long tenantId;
    private String orgTypeCode;
    private String orgTypeName;
    private OrgTypeStatus orgTypeStatus;
    private LocalDateTime createdAt;
    private Long createdBy;

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getOrgTypeCode() {
        return orgTypeCode;
    }

    public void setOrgTypeCode(String orgTypeCode) {
        this.orgTypeCode = orgTypeCode;
    }

    public String getOrgTypeName() {
        return orgTypeName;
    }

    public void setOrgTypeName(String orgTypeName) {
        this.orgTypeName = orgTypeName;
    }

    public OrgTypeStatus getOrgTypeStatus() {
        return orgTypeStatus;
    }

    public void setOrgTypeStatus(OrgTypeStatus orgTypeStatus) {
        this.orgTypeStatus = orgTypeStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
}
