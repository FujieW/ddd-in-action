package com.example.dddinaction.application.organization;

import com.example.dddinaction.common.convert.OrgConvert;
import com.example.dddinaction.domain.organization.org.OrgBuilderFactory;
import com.example.dddinaction.domain.organization.org.Organization;
import com.example.dddinaction.domain.organization.org.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    private OrgBuilderFactory orgBuilderFactory;

    private final OrgConvert orgConvert = new OrgConvert();


    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository, OrgBuilderFactory orgBuilderFactory) {
        this.organizationRepository = organizationRepository;
        this.orgBuilderFactory = orgBuilderFactory;

    }

    public OrgResponse addOrg(CreateOrgRequest request, Long userId) {
        Organization organization = orgBuilderFactory.builder()
                .tenantId(request.getTenant())
                .orgType(request.getOrgType())
                .leaderId(request.getLeader())
                .superiorId(request.getSuperior())
                .name(request.getName())
                .createdBy(userId)
                .build();

        organization = organizationRepository.save(organization);
        return orgConvert.convertToOrganizationDto(organization);
    }

}
