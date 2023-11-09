package com.example.dddinaction.application.organization;

import com.example.dddinaction.common.convert.OrgConvert;
import com.example.dddinaction.common.exception.BusinessException;
import com.example.dddinaction.domain.organization.org.OrgBuilderFactory;
import com.example.dddinaction.domain.organization.org.Organization;
import com.example.dddinaction.domain.organization.org.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    private OrgBuilderFactory orgBuilderFactory;

    private final OrgConvert orgConvert = new OrgConvert();

    private final OrganizationManager organizationManager;


    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository,
                                   OrgBuilderFactory orgBuilderFactory,
                                   OrganizationManager organizationManager) {
        this.organizationRepository = organizationRepository;
        this.orgBuilderFactory = orgBuilderFactory;
        this.organizationManager = organizationManager;

    }

    @Transactional
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

    @Transactional
    public OrgResponse updateOrgBasic(UpdateOrgRequest request, Long userId) {
        Organization organization = organizationRepository.findByTenantIdAndOrgId(request.getTenantId(), request.getOrgId())
                .orElseThrow(() -> new BusinessException("org not found"));
        organizationManager.updateOrg(organization, request.getName(), request.getLeader(), userId);
        Organization updatedOrg = organizationRepository.update(organization);
        return orgConvert.convertToOrganizationDto(updatedOrg);
    }

    @Transactional
    public OrgResponse cancelOrg(Long orgId, Long tentantId, Long userId) {
        Organization organization = organizationRepository.findByTenantIdAndOrgId(tentantId, orgId)
                .orElseThrow(() -> new BusinessException("org not found"));
        organizationManager.cancel(organization, userId);
        Organization canceledOrg =organizationRepository.update(organization);
        return orgConvert.convertToOrganizationDto(canceledOrg);
    }

}
