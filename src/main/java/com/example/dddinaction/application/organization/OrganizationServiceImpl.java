package com.example.dddinaction.application.organization;

import com.example.dddinaction.adapter.repository.user.UserRepository;
import com.example.dddinaction.common.convert.OrgConvert;
import com.example.dddinaction.common.exception.BusinessException;
import com.example.dddinaction.domain.organization.*;
import com.example.dddinaction.domain.tenant.TenantRepository;
import com.example.dddinaction.domain.tenant.TenantStatus;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;

    private OrgValidator orgValidator;

    private final OrgConvert orgConvert = new OrgConvert();


    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository, OrgValidator orgValidator) {
        this.organizationRepository = organizationRepository;
        this.orgValidator = orgValidator;
    }

    public OrgResponse addOrg(CreateOrgRequest request, Long userId) {
        orgValidator.validator(request, userId);
        Organization organization = orgConvert.convertFromCreateOrgRequest(request, userId);
        organization = organizationRepository.save(organization);
        return orgConvert.convertToOrganizationDto(organization);
    }


}
