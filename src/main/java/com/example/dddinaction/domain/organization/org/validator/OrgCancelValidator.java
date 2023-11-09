package com.example.dddinaction.domain.organization.org.validator;

import com.example.dddinaction.domain.organization.org.Organization;
import com.example.dddinaction.domain.organization.org.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgCancelValidator {
    private OrganizationRepository organizationRepository;

    @Autowired
    public OrgCancelValidator(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }


    public void cancelOrgShouldNotHasEmp(Long id) {

    }

    public void onlyEffectiveOrgCanBeCanceled(Organization organization) {
        if (!organization.isEffective()) {

        }
    }
}
