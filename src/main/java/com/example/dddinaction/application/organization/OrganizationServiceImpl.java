package com.example.dddinaction.application.organization;

import org.springframework.stereotype.Component;

@Component
public class OrganizationServiceImpl implements OrganizationService {
    public OrgResponse addOrg(CreateOrgRequest createOrgRequest, Long userId) {

        return buildOrgResponse();
    }

    private OrgResponse buildOrgResponse() {
        return OrgResponse.builder()
                .tenantId(1L)
                .name("狗剩集团")
                .superior(1L)
                .tenantId(1L)
                .orgType("开发中心")
                .build();

    }
}
