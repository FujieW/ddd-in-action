package com.example.dddinaction.application.organization;

import com.example.dddinaction.adapter.repository.organization.OrganizationRepositoryImpl;
import com.example.dddinaction.common.convert.OrgConvert;
import com.example.dddinaction.common.exception.BusinessException;
import com.example.dddinaction.domain.organization.*;
import com.example.dddinaction.domain.tenant.TenantRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

// @RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrganizationServiceTest {

    @Mock
    private OrganizationRepositoryImpl organizationRepository;

    @Mock
    private TenantRepository tenantRepository;

    @Mock
    private OrgTypeRepository orgTypeRepository;

    @Mock
    private EmpRepository empRepository;

    @Mock
    private OrgValidator orgValidator;


    @InjectMocks
    private OrganizationServiceImpl organizationService;


    private OrgConvert orgConvert;

    @BeforeEach
    public void setUp() {
        orgConvert = new OrgConvert();
    }

    @DisplayName("Test_For_Add_Org")
    @Nested
    class AddOrgInterface {
        @Test
        public void should_add_org_when_given_orgdto_and_operatorId() {
            CreateOrgRequest request = buildCreateOrgRequest();
            OrgResponse response = buildOrgResponse();
            Long userId = 1L;
            doNothing().when(orgValidator).validator(any(), anyLong());
            // when(tenantRepository.existsByIdAndStatus(anyLong(), any())).thenReturn(true);
            Organization organization = orgConvert.convertFromCreateOrgRequest(request, userId);
            when(organizationRepository.save(any())).thenReturn(organization);
            // when(orgTypeRepository.existsByCodeAndStatus(anyLong(), anyString(), any())).thenReturn(true);
            // Organization superior = getSuperior();
            // when(organizationRepository.findByIdAndStatus(anyLong(), any())).thenReturn(Optional.of(superior));
            // when(orgTypeRepository.findByCodeAndStatus(anyLong(), anyString(), any())).thenReturn(Optional.of(getSuperiorOrgType()));
            // when(empRepository.existsByIdAndStatus(anyLong(), anyLong(), any())).thenReturn(true);
            // when(organizationRepository.existsBySuperiorAndName(request.getTenant(), request.getSuperior(), request.getName())).thenReturn(false);

            OrgResponse result = organizationService.addOrg(request, userId);

            assertEquals(result.getLeader(), response.getLeader());
            assertEquals(result.getTenantId(), response.getTenantId());
            assertEquals(result.getName(), response.getName());
            assertEquals(result.getOrgType(), response.getOrgType());
            assertEquals(result.getSuperior(), response.getSuperior());
        }


    }


    private CreateOrgRequest buildCreateOrgRequest() {
        CreateOrgRequest org = CreateOrgRequest.builder().name("狗剩集团").orgType("DEVCENT").tenant(1L).superior(1L).leader(1L).build();
        return org;
    }

    private OrgResponse buildOrgResponse() {
        return OrgResponse.builder().tenantId(1L).name("狗剩集团").superior(1L).leader(1L).orgType("DEVCENT").build();
    }

    private Organization getSuperior() {
        Organization organization = new Organization();
        organization.setSuperiorId(0L);
        organization.setName("租户");
        organization.setTenantId(1L);
        organization.setStatus(OrgStatus.EFFECTIVE);
        organization.setOrgType("ENTP");
        return organization;
    }

    private OrganizationType getSuperiorOrgType() {
        OrganizationType orgType = new OrganizationType();
        orgType.setTenantId(1L);
        orgType.setOrgTypeCode("ENTP");
        orgType.setOrgTypeName("企业");
        orgType.setOrgTypeStatus(OrgTypeStatus.EFFECTIVE);
        orgType.setCreatedAt(LocalDateTime.now());
        orgType.setCreatedBy(1L);
        return orgType;
    }
}
