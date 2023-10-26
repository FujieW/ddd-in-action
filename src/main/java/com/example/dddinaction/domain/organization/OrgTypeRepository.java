package com.example.dddinaction.domain.organization;

import java.util.Optional;

public interface OrgTypeRepository {

    boolean existsByCodeAndStatus(Long tenant, String orgType, OrgStatus orgStatus);

    Optional<OrganizationType> findByCodeAndStatus(Long superior, String orgType, OrgStatus orgStatus);
}
