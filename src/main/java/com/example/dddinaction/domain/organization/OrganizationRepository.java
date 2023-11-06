package com.example.dddinaction.domain.organization;

import com.example.dddinaction.domain.organization.Organization;

import java.util.Optional;

public interface OrganizationRepository {
    Organization save(Organization organization);

    Optional<Organization> findByIdAndStatus(Long superior, OrgStatus orgStatus);

    boolean existsBySuperiorAndName(Long tenant, Long superior, String name);
}
