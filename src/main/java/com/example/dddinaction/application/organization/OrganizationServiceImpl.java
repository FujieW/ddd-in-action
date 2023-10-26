package com.example.dddinaction.application.organization;

import com.example.dddinaction.adapter.repository.organization.EmpRepositoryImpl;
import com.example.dddinaction.adapter.repository.organization.OrganizationRepositoryImpl;
import com.example.dddinaction.adapter.repository.user.UserRepository;
import com.example.dddinaction.common.convert.OrgConvert;
import com.example.dddinaction.common.exception.BusinessException;
import com.example.dddinaction.domain.organization.*;
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
    private final EmpRepository empRepositoryImpl;

    private final OrgConvert orgConvert = new OrgConvert();



    @Autowired
    public OrganizationServiceImpl(UserRepository userRepository, TenantRepository tenantRepository, OrgTypeRepository orgTypeRepository, OrganizationRepositoryImpl orgRepository, EmpRepositoryImpl empRepositoryImpl) {
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
        this.orgTypeRepository = orgTypeRepository;
        this.orgRepository = orgRepository;
        this.empRepositoryImpl = empRepositoryImpl;
    }

    public OrgResponse addOrg(CreateOrgRequest request, Long userId) {
        validator(request, userId);
        Organization organization = orgConvert.convertFromCreateOrgRequest(request, userId);
        organization = orgRepository.save(organization);
        return orgConvert.convertToOrganizationDto(organization);
    }

    private void validator(CreateOrgRequest request, Long userId) {
        if (!tenantRepository.existsByIdAndStatus(request.getTenant(), TenantStatus.EFFECTIVE)) {
            throw new BusinessException("id为'" + request.getTenant() + "'的租户不是有效租户！");
        }

        if (StringUtils.isEmpty(request.getOrgType())) {
            throw new BusinessException("组织类别不能为空！");
        }

        if ("ENTP".equals(request.getOrgType())) {
            throw new BusinessException("企业是在创建租户的时候创建好的，因此不能单独创建企业");
        }

        if (!orgTypeRepository.existsByCodeAndStatus(request.getTenant(), request.getOrgType(), OrgStatus.EFFECTIVE)) {
            throw new BusinessException("'" + request.getOrgType() + "'不是有效的组织类别代码！");
        }

        Organization superior = orgRepository.findByIdAndStatus(request.getSuperior(), OrgStatus.EFFECTIVE)
                .orElseThrow(() -> new BusinessException("'" + request.getSuperior() + "' 不是有效的组织 id !"));

        OrganizationType superiorOrgType = orgTypeRepository.findByCodeAndStatus(request.getSuperior(), superior.getOrgType(), OrgStatus.EFFECTIVE)
                .orElseThrow(() -> new BusinessException("id 为 '" + request.getSuperior() + "' 的组织的组织类型代码 '" + superior.getOrgType() + "' 无效!"));

        if (("DEVCENT".equals(request.getOrgType()) || "DIRDEP".equals(request.getOrgType())) && !"ENTP".equals(superiorOrgType.getOrgTypeCode())) {
            throw new BusinessException("开发中心或直属部门的上级(id = '" + request.getSuperior() + "')不是企业！");
        }

        // 组织负责人可以空缺，如果有的话，的必须是一个在职员工（含试用期）
        if (request.getLeader() != null && !empRepositoryImpl.existsByIdAndStatus(request.getTenant(), request.getLeader(), EmpStatus.REGULAR, EmpStatus.PROBATION)) {
            throw new BusinessException("组织负责人(id='" + request.getLeader() + "')不是在职员工！");
        }


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
