package com.example.dddinaction.domain;

import com.example.dddinaction.application.organization.CreateOrgRequest;
import com.example.dddinaction.application.organization.OrgResponse;
import com.example.dddinaction.common.exception.BusinessException;
import com.example.dddinaction.domain.organization.org.OrgStatus;
import com.example.dddinaction.domain.organization.org.validator.OrgTypeValidator;
import com.example.dddinaction.domain.organization.org.Organization;
import com.example.dddinaction.domain.organization.orgtype.OrganizationType;
import com.example.dddinaction.domain.organization.orgtype.OrgTypeRepository;
import com.example.dddinaction.domain.organization.orgtype.OrgTypeStatus;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrgTypeValidatorTest {

    @Mock
    private OrgTypeRepository orgTypeRepository;

    @InjectMocks
    private OrgTypeValidator orgTypeValidator;


    @Test
    public void should_throw_exception_when_org_type_is_null_or_empty() {
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        createOrgRequest.setOrgType(null);
        BusinessException exception = assertThrows(BusinessException.class, () -> orgTypeValidator.verify(createOrgRequest.getTenant(), createOrgRequest.getOrgType()));
        assertEquals("组织类别不能为空！", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when_add_an_enterprise() {
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        createOrgRequest.setOrgType("ENTP");
        BusinessException exception = assertThrows(BusinessException.class, () -> orgTypeValidator.verify(createOrgRequest.getTenant(), createOrgRequest.getOrgType()));
        assertEquals("企业是在创建租户的时候创建好的，因此不能单独创建企业", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when_org_type_is_not_effective() {
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        when(orgTypeRepository.existsByCodeAndStatus(anyLong(), anyString(), any())).thenReturn(false);
        BusinessException exception = assertThrows(BusinessException.class, () -> orgTypeValidator.verify(createOrgRequest.getTenant(), createOrgRequest.getOrgType()));
        assertEquals("'" + createOrgRequest.getOrgType() + "'不是有效的组织类别代码！", exception.getMessage());
    }

    private CreateOrgRequest buildCreateOrgRequest() {
        CreateOrgRequest org = CreateOrgRequest.builder().name("狗剩集团").orgType("DEVCENT").tenant(1L).superior(1L).leader(1L).build();
        return org;
    }

    private OrgResponse buildOrgResponse() {
        return OrgResponse.builder().tenantId(1L).name("狗剩集团").superior(1L).leader(1L).orgType("DEVCENT").build();
    }

    private Organization getSuperior() {
        Organization organization = new Organization(LocalDateTime.now(), 1L);
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
