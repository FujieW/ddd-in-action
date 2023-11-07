package com.example.dddinaction.domain.organization.org.validator;

import com.example.dddinaction.common.exception.BusinessException;
import com.example.dddinaction.domain.organization.org.OrganizationRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgNameValidator {

    private OrganizationRepository organizationRepository;

    @Autowired
    public OrgNameValidator(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public void verify(String name, Long tenant, Long superior) {
        // 组织必须有名称
        if (StringUtils.isEmpty(name)) {
            throw new BusinessException("组织没有名称！");
        }

        // 同一个组织下的下级组织不能重名
        if (organizationRepository.existsBySuperiorAndName(tenant, superior, name)) {
            throw new BusinessException("同一上级下已经有名为'" + name + "'的组织存在！");
        }
    }
}
