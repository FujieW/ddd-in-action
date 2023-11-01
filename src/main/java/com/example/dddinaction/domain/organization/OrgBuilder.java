package com.example.dddinaction.domain.organization;

import org.assertj.core.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrgBuilder {
    @Autowired
    private OrgCommonValidator orgCommonValidator;

    @Autowired
    private OrgLeaderValidator orgLeaderValidator;

    @Autowired
    private OrgNameValidator orgNameValidator;

    @Autowired
    private OrgSuperiorValidator orgSuperiorValidator;

    @Autowired
    private OrgTypeValidator orgTypeValidator;

    public OrgBuilder(OrgCommonValidator orgCommonValidator, OrgLeaderValidator orgLeaderValidator, OrgNameValidator orgNameValidator, OrgSuperiorValidator orgSuperiorValidator, OrgTypeValidator orgTypeValidator) {
        this.orgCommonValidator = orgCommonValidator;
        this.orgLeaderValidator = orgLeaderValidator;
        this.orgNameValidator = orgNameValidator;
        this.orgSuperiorValidator = orgSuperiorValidator;
        this.orgTypeValidator = orgTypeValidator;
    }

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

    public Organization build() {
        validate();
        Organization organization = new Organization();
        organization.setTenantId(this.tenantId);
        organization.setOrgType(this.orgType);
        organization.setSuperiorId(this.superiorId);
        organization.setLeaderId(this.leaderId);
        organization.setName(this.name);
        organization.setStatus(this.status);
        organization.setCreatedBy(this.createdBy);
        organization.setCreatedAt(this.createdAt);
        organization.setLastUpdatedAt(this.lastUpdatedAt);
        organization.setLastUpdatedBy(this.lastUpdatedBy);
        return organization;
    }

    @VisibleForTesting
    protected void validate() {
        orgCommonValidator.verify(this.tenantId);
        orgLeaderValidator.verify(this.tenantId, this.leaderId);
        orgTypeValidator.verify(this.tenantId, this.orgType);
        orgSuperiorValidator.verify(this.superiorId, this.orgType);
        orgNameValidator.verify(this.name, this.tenantId, this.superiorId);
    }

    public OrgBuilder tenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public OrgBuilder superiorId(Long superiorId) {
        this.superiorId = superiorId;
        return this;
    }

    public OrgBuilder orgType(String orgType) {
        this.orgType = orgType;
        return this;
    }

    public OrgBuilder leaderId(Long leaderId) {
        this.leaderId = leaderId;
        return this;
    }

    public OrgBuilder name(String name) {
        this.name = name;
        return this;
    }

    public OrgBuilder status(OrgStatus status) {
        this.status = status;
        return this;
    }

    public OrgBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public OrgBuilder createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public OrgBuilder lastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
        return this;
    }

    public OrgBuilder lastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }
}
