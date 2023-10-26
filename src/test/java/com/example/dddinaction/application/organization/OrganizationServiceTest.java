package com.example.dddinaction.application.organization;

import com.example.dddinaction.adapter.repository.organization.OrganizationRepositoryImpl;
import com.example.dddinaction.adapter.repository.tenant.TenantRepositoryImpl;
import com.example.dddinaction.common.convert.OrgConvert;
import com.example.dddinaction.common.exception.BusinessException;
import com.example.dddinaction.domain.organization.OrgStatus;
import com.example.dddinaction.domain.organization.OrgTypeRepository;
import com.example.dddinaction.domain.organization.Organization;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class OrganizationServiceTest {

    @Mock
    private OrganizationRepositoryImpl orgRepository;

    @Mock
    private TenantRepositoryImpl tenantRepository;

    @Mock
    private OrgTypeRepository orgTypeRepository;


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
        Mockito.when(tenantRepository.existsByIdAndStatus(Mockito.anyLong(), Mockito.any())).thenReturn(true);
        Organization organization = orgConvert.convertFromCreateOrgRequest(createOrgRequest, userId);
        Mockito.when(orgRepository.save(Mockito.any())).thenReturn(organization);
        Mockito.when(orgTypeRepository.existsByCodeAndStatus(Mockito.anyLong(), Mockito.anyString(), Mockito.any())).thenReturn(true);
        Organization superior = getSuperior();
        Mockito.when(orgRepository.findByIdAndStatus(Mockito.anyLong(), Mockito.any())).thenReturn(Optional.of(superior));
        OrgResponse result = organizationService.addOrg(createOrgRequest, userId);

        Assert.assertEquals(result.getLeader(), response.getLeader());
        Assert.assertEquals(result.getTenantId(), response.getTenantId());
        Assert.assertEquals(result.getName(), response.getName());
        Assert.assertEquals(result.getOrgType(), response.getOrgType());
        Assert.assertEquals(result.getSuperior(), response.getSuperior());
    }

    @Test
    public void should_throw_exception_when_add_a_org_into_a_terminated_tenant() {
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        Mockito.when(tenantRepository.existsByIdAndStatus(Mockito.anyLong(), Mockito.any())).thenReturn(false);
        BusinessException exception = Assert.assertThrows(BusinessException.class, () -> organizationService.addOrg(createOrgRequest, 1L));
        Assert.assertEquals("id为'" + createOrgRequest.getTenant() + "'的租户不是有效租户！", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when_org_type_is_null_or_empty() {
        Mockito.when(tenantRepository.existsByIdAndStatus(Mockito.anyLong(), Mockito.any())).thenReturn(true);
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        createOrgRequest.setOrgType(null);
        BusinessException exception = Assert.assertThrows(BusinessException.class, () -> organizationService.addOrg(createOrgRequest, 1L));
        Assert.assertEquals("组织类别不能为空！", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when_add_an_enterprise() {
        Mockito.when(tenantRepository.existsByIdAndStatus(Mockito.anyLong(), Mockito.any())).thenReturn(true);
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        createOrgRequest.setOrgType("ENTP");
        BusinessException exception = Assert.assertThrows(BusinessException.class, () -> organizationService.addOrg(createOrgRequest, 1L));
        Assert.assertEquals("企业是在创建租户的时候创建好的，因此不能单独创建企业", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when_org_type_is_not_effective() {
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        Mockito.when(tenantRepository.existsByIdAndStatus(Mockito.anyLong(), Mockito.any())).thenReturn(true);
        Mockito.when(orgTypeRepository.existsByCodeAndStatus(Mockito.anyLong(), Mockito.anyString(), Mockito.any())).thenReturn(false);
        BusinessException exception = Assert.assertThrows(BusinessException.class, () -> organizationService.addOrg(createOrgRequest, 1L));
        Assert.assertEquals("'" + createOrgRequest.getOrgType() + "'不是有效的组织类别代码！", exception.getMessage());

    }

    @Test
    public void should_throw_exception_when_superior_is_not_effective() {
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        Mockito.when(tenantRepository.existsByIdAndStatus(Mockito.anyLong(), Mockito.any())).thenReturn(true);
        Mockito.when(orgTypeRepository.existsByCodeAndStatus(Mockito.anyLong(), Mockito.anyString(), Mockito.any())).thenReturn(true);
        BusinessException exception = Assert.assertThrows(BusinessException.class, () -> organizationService.addOrg(createOrgRequest, 1L));
        Assert.assertEquals("'" + createOrgRequest.getSuperior() + "' 不是有效的组织 id !", exception.getMessage());
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

    private Organization getSuperior() {
        Organization organization = new Organization();
        organization.setSuperiorId(0L);
        organization.setName("租户");
        organization.setTenantId(1L);
        organization.setStatus(OrgStatus.EFFECTIVE);
        return organization;
    }
}
