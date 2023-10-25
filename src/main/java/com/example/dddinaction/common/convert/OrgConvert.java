package com.example.dddinaction.common.convert;

import com.example.dddinaction.application.organization.CreateOrgRequest;
import com.example.dddinaction.application.organization.OrgResponse;
import com.example.dddinaction.domain.organization.Organization;

import java.time.LocalDateTime;

public class OrgConvert {
    public Organization convertFromCreateOrgRequest(CreateOrgRequest createOrgRequest, Long userId) {
        Organization organization = new Organization();
        organization.setName(createOrgRequest.getName());
        organization.setLeaderId(createOrgRequest.getLeader());
        organization.setSuperiorId(createOrgRequest.getSuperior());
        organization.setTenantId(createOrgRequest.getTenant());
        organization.setOrgTypeCode(createOrgRequest.getOrgType());

        organization.setCreatedAt(LocalDateTime.now());
        organization.setCreatedBy(userId);
        organization.setLastUpdatedAt(LocalDateTime.now());
        organization.setCreatedBy(userId);

        return organization;
    }

    public OrgResponse convertToOrganizationDto(Organization organization) {
        return OrgResponse.builder()
                .tenantId(organization.getTenantId())
                .orgType(organization.getOrgTypeCode())
                .superior(organization.getSuperiorId())
                .leader(organization.getLeaderId())
                .name(organization.getName())
                .build();
    }
}
