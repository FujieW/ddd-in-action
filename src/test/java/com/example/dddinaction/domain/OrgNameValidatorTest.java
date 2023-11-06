package com.example.dddinaction.domain;

import com.example.dddinaction.application.organization.CreateOrgRequest;
import com.example.dddinaction.common.exception.BusinessException;
import com.example.dddinaction.domain.organization.OrgNameValidator;
import com.example.dddinaction.domain.organization.OrganizationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class OrgNameValidatorTest {

    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private OrgNameValidator orgNameValidator;


    @Test
    public void should_throw_exception_when_org_name_is_empty_or_null() {
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        createOrgRequest.setName(null);

        BusinessException exception = assertThrows(BusinessException.class, () -> orgNameValidator.verify(createOrgRequest.getName(), createOrgRequest.getTenant(), createOrgRequest.getSuperior()));
        assertEquals("组织没有名称！", exception.getMessage());
    }


    @Test
    public void should_throw_exception_when__org_name_has_been_duplicated_with_same_superior() {
        CreateOrgRequest request = buildCreateOrgRequest();
        Mockito.when(organizationRepository.existsBySuperiorAndName(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString())).thenReturn(true);
        BusinessException exception = assertThrows(BusinessException.class, () -> orgNameValidator.verify(request.getName(), request.getTenant(), request.getSuperior()));
        assertEquals("同一上级下已经有名为'狗剩集团'的组织存在！", exception.getMessage());
    }

    private CreateOrgRequest buildCreateOrgRequest() {
        CreateOrgRequest org = CreateOrgRequest.builder().name("狗剩集团").orgType("DEVCENT").tenant(1L).superior(1L).leader(1L).build();
        return org;
    }
}
