package com.example.dddinaction.domain.organization;

import com.example.dddinaction.application.organization.CreateOrgRequest;
import com.example.dddinaction.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgNameValidator extends OrgBaseValidator {

    private OrganizationRepository organizationRepository;

    @Autowired
    public OrgNameValidator(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public void verify(CreateOrgRequest request) {
        // 组织必须有名称
        if (StringUtils.isEmpty(request.getName())) {
            throw new BusinessException("组织没有名称！");
        }

        // 同一个组织下的下级组织不能重名
        if (organizationRepository.existsBySuperiorAndName(request.getTenant(), request.getSuperior(), request.getName())) {
            throw new BusinessException("同一上级下已经有名为'" + request.getName() + "'的组织存在！");
        }
    }
}
