package com.example.dddinaction.domain.organization;

import com.example.dddinaction.adapter.repository.user.UserRepository;
import com.example.dddinaction.application.organization.CreateOrgRequest;
import com.example.dddinaction.common.exception.BusinessException;
import com.example.dddinaction.domain.tenant.TenantRepository;
import com.example.dddinaction.domain.tenant.TenantStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgValidator {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final OrgTypeRepository orgTypeRepository;
    private final OrganizationRepository organizationRepository;
    private final EmpRepository empRepositoryImpl;

    @Autowired
    public OrgValidator(UserRepository userRepository, TenantRepository tenantRepository, OrgTypeRepository orgTypeRepository, OrganizationRepository organizationRepository, EmpRepository empRepositoryImpl) {
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
        this.orgTypeRepository = orgTypeRepository;
        this.organizationRepository = organizationRepository;
        this.empRepositoryImpl = empRepositoryImpl;
    }

    public void validator(CreateOrgRequest request, Long userId) {
        tentantShouldValid(request);

        verifyOrgType(request);

        verifySuperior(request);

        leaderShouldBeEffective(request);
        verifyOrgName(request);

    }

    private void verifyOrgName(CreateOrgRequest request) {
        // 组织必须有名称
        if (StringUtils.isEmpty(request.getName())) {
            throw new BusinessException("组织没有名称！");
        }

        // 同一个组织下的下级组织不能重名
        if (organizationRepository.existsBySuperiorAndName(request.getTenant(), request.getSuperior(), request.getName())) {
            throw new BusinessException("同一上级下已经有名为'" + request.getName() + "'的组织存在！");
        }
    }

    private void verifySuperior(CreateOrgRequest request) {
        Organization superior = superiorShouldEffective(request);
        OrganizationType superiorOrgType = findSuperiorOrgType(request, superior);
        superiorOfDevGrpMustBeDevCenter(request, superiorOrgType);
        superiorOfDevCentAndDirectDeptMustBeEntp(request, superiorOrgType);
    }

    private void superiorOfDevCentAndDirectDeptMustBeEntp(CreateOrgRequest request, OrganizationType superiorOrgType) {
        // 开发中心和直属部门的上级只能是企业
        if (("DEVCENT".equals(request.getOrgType()) || "DIRDEP".equals(request.getOrgType())) && !"ENTP".equals(superiorOrgType.getOrgTypeCode())) {
            throw new BusinessException("开发中心或直属部门的上级(id = '" + request.getSuperior() + "')不是企业！");
        }
    }

    private static void superiorOfDevGrpMustBeDevCenter(CreateOrgRequest request, OrganizationType superiorOrgType) {
        // 开发组的上级只能是开发中心
        if ("DEVGRP".equals(request.getOrgType()) && !"DEVCENT".equals(superiorOrgType.getOrgTypeCode())) {
            throw new BusinessException("开发组的上级(id = '" + request.getSuperior() + "')不是开发中心！");
        }
    }

    private OrganizationType findSuperiorOrgType(CreateOrgRequest request, Organization superior) {
        OrganizationType superiorOrgType = orgTypeRepository.findByCodeAndStatus(request.getSuperior(), superior.getOrgType(), OrgStatus.EFFECTIVE)
                .orElseThrow(() -> new BusinessException("id 为 '" + request.getSuperior() + "' 的组织的组织类型代码 '" + superior.getOrgType() + "' 无效!"));
        return superiorOrgType;
    }

    private Organization superiorShouldEffective(CreateOrgRequest request) {
        Organization superior = organizationRepository.findByIdAndStatus(request.getSuperior(), OrgStatus.EFFECTIVE)
                .orElseThrow(() -> new BusinessException("'" + request.getSuperior() + "' 不是有效的组织 id !"));
        return superior;
    }

    private void verifyOrgType(CreateOrgRequest request) {
        orgTypeShouldNotEmpty(request);
        shouldNotCreateEntpAlone(request);
        orgTypeShouldBeValid(request);
    }

    private void shouldNotCreateEntpAlone(CreateOrgRequest request) {
        if ("ENTP".equals(request.getOrgType())) {
            throw new BusinessException("企业是在创建租户的时候创建好的，因此不能单独创建企业");
        }
    }

    private void orgTypeShouldBeValid(CreateOrgRequest request) {
        if (!orgTypeRepository.existsByCodeAndStatus(request.getTenant(), request.getOrgType(), OrgStatus.EFFECTIVE)) {
            throw new BusinessException("'" + request.getOrgType() + "'不是有效的组织类别代码！");
        }
    }

    private void orgTypeShouldNotEmpty(CreateOrgRequest request) {
        if (StringUtils.isEmpty(request.getOrgType())) {
            throw new BusinessException("组织类别不能为空！");
        }
    }

    private void leaderShouldBeEffective(CreateOrgRequest request) {
        // 组织负责人可以空缺，如果有的话，的必须是一个在职员工（含试用期）
        if (request.getLeader() != null && !empRepositoryImpl.existsByIdAndStatus(request.getTenant(), request.getLeader(), EmpStatus.REGULAR, EmpStatus.PROBATION)) {
            throw new BusinessException("组织负责人(id='" + request.getLeader() + "')不是在职员工！");
        }
    }

    private void tentantShouldValid(CreateOrgRequest request) {
        if (!tenantRepository.existsByIdAndStatus(request.getTenant(), TenantStatus.EFFECTIVE)) {
            throw new BusinessException("id为'" + request.getTenant() + "'的租户不是有效租户！");
        }
    }
}
