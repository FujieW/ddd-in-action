package com.example.dddinaction.domain.organization.org.validator;

import com.example.dddinaction.common.exception.BusinessException;
import com.example.dddinaction.domain.organization.org.OrgStatus;
import com.example.dddinaction.domain.organization.orgtype.OrgTypeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgTypeValidator {

    private OrgTypeRepository orgTypeRepository;

    @Autowired
    public OrgTypeValidator(OrgTypeRepository orgTypeRepository) {
        this.orgTypeRepository = orgTypeRepository;
    }

    public void verify(Long tenant, String orgType) {
        orgTypeShouldNotEmpty(orgType);
        shouldNotCreateEntpAlone(orgType);
        orgTypeShouldBeValid(tenant, orgType);
    }

    private void shouldNotCreateEntpAlone(String orgType) {
        if ("ENTP".equals(orgType)) {
            throw new BusinessException("企业是在创建租户的时候创建好的，因此不能单独创建企业");
        }
    }

    private void orgTypeShouldBeValid(Long tenant, String orgType) {
        if (!orgTypeRepository.existsByCodeAndStatus(tenant, orgType, OrgStatus.EFFECTIVE)) {
            throw new BusinessException("'" + orgType + "'不是有效的组织类别代码！");
        }
    }

    private void orgTypeShouldNotEmpty(String orgType) {
        if (StringUtils.isEmpty(orgType)) {
            throw new BusinessException("组织类别不能为空！");
        }
    }


}
