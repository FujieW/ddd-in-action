package com.example.dddinaction.adapter.repository.organization;

import com.example.dddinaction.domain.organization.org.OrgStatus;
import com.example.dddinaction.domain.organization.org.OrganizationRepository;
import com.example.dddinaction.domain.organization.org.Organization;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrganizationRepositoryImpl implements OrganizationRepository {

    public Organization save(Organization organization) {
        return null;
    }

    @Override
    public Optional<Organization> findByIdAndStatus(Long id, OrgStatus orgStatus) {
        return Optional.empty();
    }

    @Override
    public boolean existsBySuperiorAndName(Long tenant, Long superior, String name) {
        return true;
    }
}
