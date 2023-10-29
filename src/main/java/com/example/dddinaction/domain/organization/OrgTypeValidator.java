package com.example.dddinaction.domain.organization;

import com.example.dddinaction.application.organization.CreateOrgRequest;
import com.example.dddinaction.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgTypeValidator extends OrgBaseValidator {

    private OrgTypeRepository orgTypeRepository;

    @Autowired
    public OrgTypeValidator(OrgTypeRepository orgTypeRepository) {
        this.orgTypeRepository = orgTypeRepository;
    }

    @Override
    public void verify(CreateOrgRequest request) {
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


}
