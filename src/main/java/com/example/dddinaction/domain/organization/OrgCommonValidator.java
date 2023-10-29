package com.example.dddinaction.domain.organization;

import com.example.dddinaction.common.exception.BusinessException;
import com.example.dddinaction.domain.tenant.TenantRepository;
import com.example.dddinaction.domain.tenant.TenantStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgCommonValidator {


    @Autowired
    private TenantRepository tenantRepository;

    public void verify(Long tenantId) {
        if (!tenantRepository.existsByIdAndStatus(tenantId, TenantStatus.EFFECTIVE)) {
            throw new BusinessException("id为'" + tenantId + "'的租户不是有效租户！");
        }
    }
}
