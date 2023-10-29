package com.example.dddinaction.domain.organization;

import com.example.dddinaction.application.organization.CreateOrgRequest;
import com.example.dddinaction.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgSuperiorValidator extends OrgBaseValidator {

    private OrgTypeRepository orgTypeRepository;

    private OrganizationRepository organizationRepository;

    @Autowired
    public OrgSuperiorValidator(OrgTypeRepository orgTypeRepository, OrganizationRepository organizationRepository) {
        this.orgTypeRepository = orgTypeRepository;
        this.organizationRepository = organizationRepository;
    }

    public void verify(CreateOrgRequest request) {
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
}
