package com.example.dddinaction.application.organization;

import com.example.dddinaction.domain.organization.org.*;

import com.example.dddinaction.domain.organization.org.validator.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class OrganizationServiceTest {

    @Mock
    private OrgCommonValidator orgCommonValidator;

    @Mock
    private OrgLeaderValidator orgLeaderValidator;

    @Mock
    private OrgNameValidator orgNameValidator;

    @Mock
    private OrgSuperiorValidator orgSuperiorValidator;

    @Mock
    private OrgTypeValidator orgTypeValidator;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private OrgBuilderFactory orgBuilderFactory;

    @InjectMocks
    private OrganizationServiceImpl organizationService;

    @DisplayName("Test_For_Add_Org")
    @Nested
    class AddOrgInterface {
        @Test
        public void should_add_org_when_given_orgdto_and_operatorId() {
            CreateOrgRequest request = buildCreateOrgRequest();
            OrgResponse response = buildOrgResponse();
            Long userId = 1L;
            Organization organization = buildOrganization();
            doNothing().when(orgCommonValidator).verify(request.getTenant());
            doNothing().when(orgLeaderValidator).verify(request.getTenant(), request.getLeader());
            doNothing().when(orgTypeValidator).verify(request.getTenant(), request.getOrgType());
            doNothing().when(orgSuperiorValidator).verify(request.getSuperior(), request.getOrgType());
            doNothing().when(orgNameValidator).verify(request.getName(), request.getTenant(), request.getSuperior());
            when(organizationRepository.save(any())).thenReturn(organization);
            OrgBuilder orgBuilder = Mockito.mock(OrgBuilder.class);
            when(orgBuilderFactory.builder()).thenReturn(orgBuilder);
            Mockito.when(orgBuilder.tenantId(request.getTenant())).thenReturn(orgBuilder);
            Mockito.when(orgBuilder.orgType(request.getOrgType())).thenReturn(orgBuilder);
            Mockito.when(orgBuilder.leaderId(request.getLeader())).thenReturn(orgBuilder);
            Mockito.when(orgBuilder.superiorId(request.getSuperior())).thenReturn(orgBuilder);
            Mockito.when(orgBuilder.name(request.getName())).thenReturn(orgBuilder);
            Mockito.when(orgBuilder.createdBy(userId)).thenReturn(orgBuilder);
            Mockito.when(orgBuilder.build()).thenReturn(organization);


            OrgResponse result = organizationService.addOrg(request, userId);

            assertEquals(result.getLeader(), response.getLeader());
            assertEquals(result.getTenantId(), response.getTenantId());
            assertEquals(result.getName(), response.getName());
            assertEquals(result.getOrgType(), response.getOrgType());
            assertEquals(result.getSuperior(), response.getSuperior());
        }

    }

    private Organization buildOrganization() {
        Organization organization = new Organization();
        organization.setLeaderId(1L);
        organization.setTenantId(1L);
        organization.setSuperiorId(1L);
        organization.setName("狗剩集团");
        organization.setOrgType("DEVCENT");
        return organization;
    }


    private CreateOrgRequest buildCreateOrgRequest() {
        CreateOrgRequest org = CreateOrgRequest.builder().name("狗剩集团").orgType("DEVCENT").tenant(1L).superior(1L).leader(1L).build();
        return org;
    }

    private OrgResponse buildOrgResponse() {
        return OrgResponse.builder().tenantId(1L).name("狗剩集团").superior(1L).leader(1L).orgType("DEVCENT").build();
    }
}
