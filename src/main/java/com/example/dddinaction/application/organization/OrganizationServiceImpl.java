package com.example.dddinaction.application.organization;

import com.example.dddinaction.adapter.repository.organization.EmpRepository;
import com.example.dddinaction.adapter.repository.organization.OrganizationRepositoryImpl;
import com.example.dddinaction.adapter.repository.tenant.TenantRepositoryImpl;
import com.example.dddinaction.adapter.repository.user.UserRepository;
import com.example.dddinaction.common.convert.OrgConvert;
import com.example.dddinaction.common.exception.BusinessException;
import com.example.dddinaction.domain.organization.OrgStatus;
import com.example.dddinaction.domain.organization.OrgTypeRepository;
import com.example.dddinaction.domain.organization.Organization;
import com.example.dddinaction.domain.organization.OrganizationRepository;
import com.example.dddinaction.domain.tenant.TenantRepository;
import com.example.dddinaction.domain.tenant.TenantStatus;
import org.apache.commons.lang3.StringUtils;
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
        if (!tenantRepository.existsByIdAndStatus(createOrgRequest.getTenant(), TenantStatus.EFFECTIVE)) {
            throw new BusinessException("id为'" + createOrgRequest.getTenant() + "'的租户不是有效租户！");
        }

        if (StringUtils.isEmpty(createOrgRequest.getOrgType())) {
            throw new BusinessException("组织类别不能为空！");
        }

        if ("ENTP".equals(createOrgRequest.getOrgType())) {
            throw new BusinessException("企业是在创建租户的时候创建好的，因此不能单独创建企业");
        }

        if (!orgTypeRepository.existsByCodeAndStatus(createOrgRequest.getTenant(), createOrgRequest.getOrgType(), OrgStatus.EFFECTIVE)) {
            throw new BusinessException("'" + createOrgRequest.getOrgType() + "'不是有效的组织类别代码！");
        }

        Organization superior = orgRepository.findByIdAndStatus(createOrgRequest.getSuperior(), OrgStatus.EFFECTIVE)
                .orElseThrow(() -> new BusinessException("'" + createOrgRequest.getSuperior() + "' 不是有效的组织 id !"));



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
