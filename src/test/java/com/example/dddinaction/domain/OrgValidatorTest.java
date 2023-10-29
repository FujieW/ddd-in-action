package com.example.dddinaction.domain;

import com.example.dddinaction.adapter.repository.user.UserRepository;
import com.example.dddinaction.application.organization.CreateOrgRequest;
import com.example.dddinaction.application.organization.OrgResponse;
import com.example.dddinaction.common.exception.BusinessException;
import com.example.dddinaction.domain.organization.*;
import com.example.dddinaction.domain.tenant.TenantRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrgValidatorTest {
    @Mock
    private  UserRepository userRepository;
    @Mock
    private  TenantRepository tenantRepository;
    @Mock
    private  OrgTypeRepository orgTypeRepository;
    @Mock
    private  OrganizationRepository organizationRepository;
    @Mock
    private  EmpRepository empRepository;

    @InjectMocks
    private OrgValidator orgValidator;

    @Test
    public void should_throw_exception_when_add_a_org_into_a_terminated_tenant() {
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        when(tenantRepository.existsByIdAndStatus(anyLong(), any())).thenReturn(false);
        BusinessException exception = assertThrows(BusinessException.class, () -> orgValidator.validator(createOrgRequest, 1L));
        assertEquals("id为'" + createOrgRequest.getTenant() + "'的租户不是有效租户！", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when_org_type_is_null_or_empty() {
        when(tenantRepository.existsByIdAndStatus(anyLong(), any())).thenReturn(true);
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        createOrgRequest.setOrgType(null);
        BusinessException exception = assertThrows(BusinessException.class, () -> orgValidator.validator(createOrgRequest, 1L));
        assertEquals("组织类别不能为空！", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when_add_an_enterprise() {
        when(tenantRepository.existsByIdAndStatus(anyLong(), any())).thenReturn(true);
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        createOrgRequest.setOrgType("ENTP");
        BusinessException exception = assertThrows(BusinessException.class, () -> orgValidator.validator(createOrgRequest, 1L));
        assertEquals("企业是在创建租户的时候创建好的，因此不能单独创建企业", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when_org_type_is_not_effective() {
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        when(tenantRepository.existsByIdAndStatus(anyLong(), any())).thenReturn(true);
        when(orgTypeRepository.existsByCodeAndStatus(anyLong(), anyString(), any())).thenReturn(false);
        BusinessException exception = assertThrows(BusinessException.class, () -> orgValidator.validator(createOrgRequest, 1L));
        assertEquals("'" + createOrgRequest.getOrgType() + "'不是有效的组织类别代码！", exception.getMessage());

    }

    @Test
    public void should_throw_exception_when_superior_is_not_effective() {
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        when(tenantRepository.existsByIdAndStatus(anyLong(), any())).thenReturn(true);
        when(orgTypeRepository.existsByCodeAndStatus(anyLong(), anyString(), any())).thenReturn(true);
        BusinessException exception = assertThrows(BusinessException.class, () -> orgValidator.validator(createOrgRequest, 1L));
        assertEquals("'" + createOrgRequest.getSuperior() + "' 不是有效的组织 id !", exception.getMessage());
    }


    @Test
    public void should_throw_exception_when_superior_org_type_is_not_effective() {
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        Organization superior = getSuperior();
        OrganizationType superiorOrgType = getSuperiorOrgType();
        when(tenantRepository.existsByIdAndStatus(anyLong(), any())).thenReturn(true);
        when(orgTypeRepository.existsByCodeAndStatus(anyLong(), anyString(), any())).thenReturn(true);
        when(organizationRepository.findByIdAndStatus(anyLong(), any())).thenReturn(Optional.of(superior));
        when(orgTypeRepository.findByCodeAndStatus(anyLong(), anyString(), any())).thenReturn(Optional.ofNullable(null));
        BusinessException exception = assertThrows(BusinessException.class, () -> orgValidator.validator(createOrgRequest, 1L));
        assertEquals("id 为 '" + createOrgRequest.getSuperior() + "' 的组织的组织类型代码 '" + superior.getOrgType() + "' 无效!", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when_dev_center_is_not_the_superior_of_dev_group() {
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        Organization superior = getSuperior();
        OrganizationType superiorOrgType = getSuperiorOrgType();
        superiorOrgType.setOrgTypeCode("lalala");
        when(tenantRepository.existsByIdAndStatus(anyLong(), any())).thenReturn(true);
        when(orgTypeRepository.existsByCodeAndStatus(anyLong(), anyString(), any())).thenReturn(true);
        when(organizationRepository.findByIdAndStatus(anyLong(), any())).thenReturn(Optional.of(superior));
        when(orgTypeRepository.findByCodeAndStatus(anyLong(), anyString(), any())).thenReturn(Optional.of(superiorOrgType));
        BusinessException exception = assertThrows(BusinessException.class, () -> orgValidator.validator(createOrgRequest, 1L));
        assertEquals("开发中心或直属部门的上级(id = '" + createOrgRequest.getSuperior() + "')不是企业！", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when_org_leader_is_not_a_regular_or_probation_employee() {
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        Organization superior = getSuperior();
        OrganizationType superiorOrgType = getSuperiorOrgType();
        when(tenantRepository.existsByIdAndStatus(anyLong(), any())).thenReturn(true);
        when(orgTypeRepository.existsByCodeAndStatus(anyLong(), anyString(), any())).thenReturn(true);
        when(organizationRepository.findByIdAndStatus(anyLong(), any())).thenReturn(Optional.of(superior));
        when(orgTypeRepository.findByCodeAndStatus(anyLong(), anyString(), any())).thenReturn(Optional.of(superiorOrgType));
        when(empRepository.existsByIdAndStatus(anyLong(), anyLong(), any())).thenReturn(false);
        BusinessException exception = assertThrows(BusinessException.class, () -> orgValidator.validator(createOrgRequest, 1L));
        assertEquals("组织负责人(id='" + createOrgRequest.getLeader() + "')不是在职员工！", exception.getMessage());
    }


    @Test
    public void should_throw_exception_when_org_name_is_empty_or_null() {
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        createOrgRequest.setName(null);
        Organization superior = getSuperior();
        OrganizationType superiorOrgType = getSuperiorOrgType();
        when(tenantRepository.existsByIdAndStatus(anyLong(), any())).thenReturn(true);
        when(orgTypeRepository.existsByCodeAndStatus(anyLong(), anyString(), any())).thenReturn(true);
        when(organizationRepository.findByIdAndStatus(anyLong(), any())).thenReturn(Optional.of(superior));
        when(orgTypeRepository.findByCodeAndStatus(anyLong(), anyString(), any())).thenReturn(Optional.of(superiorOrgType));
        when(empRepository.existsByIdAndStatus(anyLong(), anyLong(), any())).thenReturn(true);
        BusinessException exception = assertThrows(BusinessException.class, () -> orgValidator.validator(createOrgRequest, 1L));
        assertEquals("组织没有名称！", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when__org_name_has_been_duplicated_with_same_superior() {
        CreateOrgRequest request = buildCreateOrgRequest();
        Organization superior = getSuperior();
        OrganizationType superiorOrgType = getSuperiorOrgType();
        when(tenantRepository.existsByIdAndStatus(anyLong(), any())).thenReturn(true);
        when(orgTypeRepository.existsByCodeAndStatus(anyLong(), anyString(), any())).thenReturn(true);
        when(organizationRepository.findByIdAndStatus(anyLong(), any())).thenReturn(Optional.of(superior));
        when(orgTypeRepository.findByCodeAndStatus(anyLong(), anyString(), any())).thenReturn(Optional.of(superiorOrgType));
        when(empRepository.existsByIdAndStatus(anyLong(), anyLong(), any())).thenReturn(true);
        when(organizationRepository.existsBySuperiorAndName(request.getTenant(), request.getSuperior(), request.getName())).thenReturn(true);
        BusinessException exception = assertThrows(BusinessException.class, () -> orgValidator.validator(request, 1L));
        assertEquals("同一上级下已经有名为'狗剩集团'的组织存在！", exception.getMessage());
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
