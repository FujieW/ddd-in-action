package com.example.dddinaction.domain.organization.org;

import java.util.Optional;

public interface OrganizationRepository {
    Organization save(Organization organization);

    Optional<Organization> findByIdAndStatus(Long superior, OrgStatus orgStatus);

    boolean existsBySuperiorAndName(Long tenant, Long superior, String name);

    Optional<Organization> findByTenantIdAndOrgId(Long tenantId, Long orgId);

    Organization update(Organization organization);
}
