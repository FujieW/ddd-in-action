package com.example.dddinaction.domain;

import com.example.dddinaction.application.organization.CreateOrgRequest;
import com.example.dddinaction.common.exception.BusinessException;
import com.example.dddinaction.domain.organization.OrgCommonValidator;
import com.example.dddinaction.domain.tenant.TenantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CommonValidatorTest {

    @Mock
    TenantRepository tenantRepository;

    @InjectMocks
    private OrgCommonValidator orgCommonValidator;

    @Test
    public void should_throw_exception_when_add_a_org_into_a_terminated_tenant() {
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        when(tenantRepository.existsByIdAndStatus(anyLong(), any())).thenReturn(false);
        BusinessException exception = assertThrows(BusinessException.class, () -> orgCommonValidator.verify(createOrgRequest));
        assertEquals("id为'" + createOrgRequest.getTenant() + "'的租户不是有效租户！", exception.getMessage());
    }

    @Test
    public void should_do_nothing_when_tenant_is_effective() {
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        when(tenantRepository.existsByIdAndStatus(anyLong(), any())).thenReturn(true);
        orgCommonValidator.verify(createOrgRequest);
    }

    private CreateOrgRequest buildCreateOrgRequest() {
        CreateOrgRequest org = CreateOrgRequest.builder().name("狗剩集团").orgType("DEVCENT").tenant(1L).superior(1L).leader(1L).build();
        return org;
    }

}
