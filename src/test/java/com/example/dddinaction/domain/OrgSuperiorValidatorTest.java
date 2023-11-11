package com.example.dddinaction.domain;

import com.example.dddinaction.application.organization.CreateOrgRequest;
import com.example.dddinaction.common.exception.BusinessException;
import com.example.dddinaction.domain.organization.org.*;
import com.example.dddinaction.domain.organization.org.validator.OrgSuperiorValidator;
import com.example.dddinaction.domain.organization.orgtype.OrgTypeRepository;
import com.example.dddinaction.domain.organization.orgtype.OrgTypeStatus;
import com.example.dddinaction.domain.organization.orgtype.OrganizationType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrgSuperiorValidatorTest {

    @Mock
    private OrgTypeRepository orgTypeRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private OrgSuperiorValidator orgSuperiorValidator;

    @Test
    public void should_throw_exception_when_superior_org_type_is_not_effective() {
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        Organization superior = getSuperior();
        when(organizationRepository.findByIdAndStatus(anyLong(), any())).thenReturn(Optional.ofNullable(superior));
        when(orgTypeRepository.findByCodeAndStatus(anyLong(), anyString(), any())).thenReturn(Optional.ofNullable(null));
        BusinessException exception = assertThrows(BusinessException.class, () -> orgSuperiorValidator.verify(createOrgRequest.getSuperior(), createOrgRequest.getOrgType()));
        assertEquals("id 为 '" + createOrgRequest.getSuperior() + "' 的组织的组织类型代码 '" + superior.getOrgType() + "' 无效!", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when_dev_center_is_not_the_superior_of_dev_group() {
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        Organization superior = getSuperior();
        OrganizationType superiorOrgType = getSuperiorOrgType();
        superiorOrgType.setOrgTypeCode("lalala");
        when(orgTypeRepository.existsByCodeAndStatus(anyLong(), anyString(), any())).thenReturn(true);
        when(organizationRepository.findByIdAndStatus(anyLong(), any())).thenReturn(Optional.of(superior));
        when(orgTypeRepository.findByCodeAndStatus(anyLong(), anyString(), any())).thenReturn(Optional.of(superiorOrgType));
        BusinessException exception = assertThrows(BusinessException.class, () -> orgSuperiorValidator.verify(createOrgRequest.getSuperior(), createOrgRequest.getOrgType()));
        assertEquals("开发中心或直属部门的上级(id = '" + createOrgRequest.getSuperior() + "')不是企业！", exception.getMessage());
    }

    private CreateOrgRequest buildCreateOrgRequest() {
        CreateOrgRequest org = CreateOrgRequest.builder().name("狗剩集团").orgType("DEVCENT").tenant(1L).superior(1L).leader(1L).build();
        return org;
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
