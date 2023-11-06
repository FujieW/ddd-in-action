package com.example.dddinaction.adapter.repository.tenant;

import com.example.dddinaction.domain.tenant.TenantRepository;
import com.example.dddinaction.domain.tenant.TenantStatus;
import org.springframework.stereotype.Component;

@Component
public class TenantRepositoryImpl implements TenantRepository {
    public boolean existsByIdAndStatus(Long tenantId, TenantStatus tenantStatus) {
        return false;
    }
}
