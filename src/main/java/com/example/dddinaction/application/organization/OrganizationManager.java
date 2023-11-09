package com.example.dddinaction.application.organization;

import com.example.dddinaction.domain.organization.org.OrgStatus;
import com.example.dddinaction.domain.organization.org.Organization;
import com.example.dddinaction.domain.organization.org.validator.OrgCancelValidator;
import com.example.dddinaction.domain.organization.org.validator.OrgCommonValidator;
import com.example.dddinaction.domain.organization.org.validator.OrgLeaderValidator;
import com.example.dddinaction.domain.organization.org.validator.OrgNameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrganizationManager {

    private OrgCommonValidator orgCommonValidator;
    private OrgNameValidator orgNameValidator;
    private OrgLeaderValidator orgLeaderValidator;
    private OrgCancelValidator orgCancelValidator;

    @Autowired
    public OrganizationManager(OrgCommonValidator orgCommonValidator,
                               OrgNameValidator orgNameValidator,
                               OrgLeaderValidator orgLeaderValidator,
                                OrgCancelValidator orgCancelValidator) {
        this.orgCommonValidator = orgCommonValidator;
        this.orgNameValidator = orgNameValidator;
        this.orgLeaderValidator = orgLeaderValidator;
        this.orgCancelValidator = orgCancelValidator;
    }

    public void updateOrg(Organization organization, String name, Long leader, Long userId) {
        updateName(organization, name);
        updateLeader(organization, leader);
        updateAuditInfo(organization, userId);
    }

    private void updateAuditInfo(Organization organization, Long userId) {
        organization.setLastUpdatedBy(userId);
        organization.setLastUpdatedAt(LocalDateTime.now());
    }

    private void updateLeader(Organization organization, Long leader) {
        organization.setLeaderId(leader);
    }

    private void updateName(Organization organization, String name) {

        organization.setName(name);
    }

    public void cancel(Organization organization, Long userId) {
        orgCancelValidator.cancelOrgShouldNotHasEmp(organization.getId());
        orgCancelValidator.onlyEffectiveOrgCanBeCanceled(organization);
        organization.cancel();
        updateAuditInfo(organization, userId);
    }
}
