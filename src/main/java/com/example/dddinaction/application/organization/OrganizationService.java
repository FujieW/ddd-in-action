package com.example.dddinaction.application.organization;

public interface OrganizationService {
    OrgResponse addOrg(CreateOrgRequest createOrgRequest, Long userId);

    OrgResponse updateOrgBasic(UpdateOrgRequest request, Long userId);

}
