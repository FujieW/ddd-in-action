package com.example.dddinaction.adapter.repository.organization;

import com.example.dddinaction.adapter.repository.organization.dao.OrganizationDAO;
import com.example.dddinaction.adapter.repository.organization.dataobject.OrganizationDO;
import com.example.dddinaction.domain.organization.OrgStatus;
import com.example.dddinaction.domain.organization.OrganizationRepository;
import com.example.dddinaction.domain.organization.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
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
