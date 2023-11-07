package com.example.dddinaction.adapter.repository.organization;

import com.example.dddinaction.domain.organization.org.OrgStatus;
import com.example.dddinaction.domain.organization.orgtype.OrgTypeRepository;
import com.example.dddinaction.domain.organization.orgtype.OrganizationType;
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
