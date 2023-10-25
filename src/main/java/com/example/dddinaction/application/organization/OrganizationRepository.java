package com.example.dddinaction.application.organization;

import com.example.dddinaction.domain.organization.Organization;

public interface OrganizationRepository {
    Organization save(Organization organization);
}
