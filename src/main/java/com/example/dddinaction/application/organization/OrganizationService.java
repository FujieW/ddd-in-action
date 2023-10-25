package com.example.dddinaction.application.organization;

import com.example.dddinaction.domain.organization.Organization;

public interface OrganizationService {
    OrgResponse addOrg(CreateOrgRequest createOrgRequest, Long userId);
}
