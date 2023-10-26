package com.example.dddinaction.adapter.repository.organization;

import com.example.dddinaction.domain.organization.OrgStatus;
import com.example.dddinaction.domain.organization.OrgTypeRepository;
import com.example.dddinaction.domain.organization.Organization;
import com.example.dddinaction.domain.organization.OrganizationType;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrgTypeRepositoryImpl implements OrgTypeRepository {
    public boolean existsByCodeAndStatus(Long tenant, String orgType, OrgStatus orgStatus) {
        return false;
    }

    @Override
    public Optional<OrganizationType> findByCodeAndStatus(Long superior, String orgType, OrgStatus orgStatus) {
        return Optional.empty();
    }
}
