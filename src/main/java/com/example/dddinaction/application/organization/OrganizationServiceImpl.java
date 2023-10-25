package com.example.dddinaction.application.organization;

import com.example.dddinaction.adapter.repository.organization.EmpRepository;
import com.example.dddinaction.adapter.repository.organization.OrgTypeRepository;
import com.example.dddinaction.adapter.repository.organization.OrganizationRepositoryImpl;
import com.example.dddinaction.adapter.repository.tenant.TenantRepository;
import com.example.dddinaction.adapter.repository.user.UserRepository;
import com.example.dddinaction.common.convert.OrgConvert;
import com.example.dddinaction.domain.organization.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrganizationServiceImpl implements OrganizationService {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final OrgTypeRepository orgTypeRepository;
    private final OrganizationRepository orgRepository;
    private final EmpRepository empRepository;

    private final OrgConvert orgConvert = new OrgConvert();



    @Autowired
    public OrganizationServiceImpl(UserRepository userRepository, TenantRepository tenantRepository, OrgTypeRepository orgTypeRepository, OrganizationRepositoryImpl orgRepository, EmpRepository empRepository) {
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
        this.orgTypeRepository = orgTypeRepository;
        this.orgRepository = orgRepository;
        this.empRepository = empRepository;
    }

    public OrgResponse addOrg(CreateOrgRequest createOrgRequest, Long userId) {
        validator(createOrgRequest, userId);
        Organization organization = orgConvert.convertFromCreateOrgRequest(createOrgRequest, userId);
        organization = orgRepository.save(organization);
        return orgConvert.convertToOrganizationDto(organization);
    }

    private void validator(CreateOrgRequest createOrgRequest, Long userId) {

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
