package com.example.dddinaction.adapter.repository.organization;

import com.example.dddinaction.domain.organization.OrgStatus;
import com.example.dddinaction.domain.organization.OrganizationRepository;
import com.example.dddinaction.domain.organization.Organization;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrganizationRepositoryImpl implements OrganizationRepository {
    public Organization save(Organization organization) {
        return null;
    }

    @Override
    public Optional<Organization> findByIdAndStatus(Long superior, OrgStatus orgStatus) {
        return Optional.empty();
    }
}
