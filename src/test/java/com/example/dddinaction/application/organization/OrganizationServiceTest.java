package com.example.dddinaction.application.organization;

import com.example.dddinaction.adapter.repository.organization.EmpRepository;
import com.example.dddinaction.adapter.repository.organization.OrgTypeRepository;
import com.example.dddinaction.adapter.repository.organization.OrganizationRepository;
import com.example.dddinaction.adapter.repository.tenant.TenantRepository;
import com.example.dddinaction.adapter.repository.user.UserRepository;
import com.example.dddinaction.common.convert.OrgConvert;
import com.example.dddinaction.domain.organization.Organization;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrganizationServiceTest {

    @Mock
    private OrganizationRepository orgRepository;

    @InjectMocks
    private OrganizationServiceImpl organizationService;


    private OrgConvert orgConvert;

    @Before
    public void setUp() {
        orgConvert = new OrgConvert();
    }

    @Test
    public void should_add_org_when_given_orgdto_and_operatorId() {
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        OrgResponse response = buildOrgResponse();
        Long userId = 1L;
        Mockito.when(orgRepository.save(Mockito.any())).thenReturn(orgConvert.convertFromCreateOrgRequest(createOrgRequest, userId));
        OrgResponse result = organizationService.addOrg(createOrgRequest, userId);

        Assert.assertEquals(result.getLeader(), response.getLeader());
        Assert.assertEquals(result.getTenantId(), response.getTenantId());
        Assert.assertEquals(result.getName(), response.getName());
        Assert.assertEquals(result.getOrgType(), response.getOrgType());
        Assert.assertEquals(result.getSuperior(), response.getSuperior());
    }

    private CreateOrgRequest buildCreateOrgRequest() {
        CreateOrgRequest org = CreateOrgRequest.builder()
                .name("狗剩集团")
                .orgType("开发中心")
                .tenant(1L)
                .superior(1L)
                .leader(1L)
                .build();
        return org;
    }

    private OrgResponse buildOrgResponse() {
        return OrgResponse.builder()
                .tenantId(1L)
                .name("狗剩集团")
                .superior(1L)
                .leader(1L)
                .orgType("开发中心")
                .build();

    }
}
