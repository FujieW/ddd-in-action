package com.example.dddinaction.domain.organization.orgtype;

import com.example.dddinaction.domain.organization.org.OrgStatus;

import java.util.Optional;

public interface OrgTypeRepository {

    boolean existsByCodeAndStatus(Long tenant, String orgType, OrgStatus orgStatus);

    Optional<OrganizationType> findByCodeAndStatus(Long superior, String orgType, OrgStatus orgStatus);
}
