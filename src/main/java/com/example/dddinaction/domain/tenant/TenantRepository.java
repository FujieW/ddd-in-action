package com.example.dddinaction.domain.tenant;

public interface TenantRepository {
    boolean existsByIdAndStatus(Long tenantId, TenantStatus tenantStatus);
}
