package com.example.dddinaction.domain.organization;

import com.example.dddinaction.application.organization.CreateOrgRequest;
import com.example.dddinaction.common.exception.BusinessException;
import com.example.dddinaction.domain.tenant.TenantRepository;
import com.example.dddinaction.domain.tenant.TenantStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgCommonValidator extends OrgBaseValidator {

    private final TenantRepository tenantRepository;

    @Autowired
    public OrgCommonValidator(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public void verify(CreateOrgRequest request) {
        if (!tenantRepository.existsByIdAndStatus(request.getTenant(), TenantStatus.EFFECTIVE)) {
            throw new BusinessException("id为'" + request.getTenant() + "'的租户不是有效租户！");
        }
    }
}
