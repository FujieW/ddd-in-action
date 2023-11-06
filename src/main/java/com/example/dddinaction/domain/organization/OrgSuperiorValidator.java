package com.example.dddinaction.domain.organization;

import com.example.dddinaction.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgSuperiorValidator{

    private OrgTypeRepository orgTypeRepository;

    private OrganizationRepository organizationRepository;

    @Autowired
    public OrgSuperiorValidator(OrgTypeRepository orgTypeRepository, OrganizationRepository organizationRepository) {
        this.orgTypeRepository = orgTypeRepository;
        this.organizationRepository = organizationRepository;
    }

    public void verify(Long superiorId, String orgType) {
        Organization superior = superiorShouldEffective(superiorId);
        OrganizationType superiorOrgType = findSuperiorOrgType(superior, superiorId);
        superiorOfDevGrpMustBeDevCenter(superiorOrgType, superiorId, orgType);
        superiorOfDevCentAndDirectDeptMustBeEntp(superiorOrgType, superiorId, orgType);
    }

    private void superiorOfDevCentAndDirectDeptMustBeEntp(OrganizationType superiorOrgType, Long superiorId, String orgTypeCode) {
        // 开发中心和直属部门的上级只能是企业
        if (("DEVCENT".equals(orgTypeCode) || "DIRDEP".equals(orgTypeCode)) && !"ENTP".equals(superiorOrgType.getOrgTypeCode())) {
            throw new BusinessException("开发中心或直属部门的上级(id = '" + superiorId + "')不是企业！");
        }
    }

    private static void superiorOfDevGrpMustBeDevCenter(OrganizationType superiorOrgType, Long superiorId, String orgTypeCode) {
        // 开发组的上级只能是开发中心
        if ("DEVGRP".equals(orgTypeCode) && !"DEVCENT".equals(superiorOrgType.getOrgTypeCode())) {
            throw new BusinessException("开发组的上级(id = '" + superiorId + "')不是开发中心！");
        }
    }

    private OrganizationType findSuperiorOrgType(Organization superior, Long superiorId) {
        OrganizationType superiorOrgType = orgTypeRepository.findByCodeAndStatus(superiorId, superior.getOrgType(), OrgStatus.EFFECTIVE)
                .orElseThrow(() -> new BusinessException("id 为 '" + superiorId + "' 的组织的组织类型代码 '" + superior.getOrgType() + "' 无效!"));
        return superiorOrgType;
    }

    private Organization superiorShouldEffective(Long superiorId) {
        Organization superior = organizationRepository.findByIdAndStatus(superiorId, OrgStatus.EFFECTIVE)
                .orElseThrow(() -> new BusinessException("'" + superiorId + "' 不是有效的组织 id !"));
        return superior;
    }
}
