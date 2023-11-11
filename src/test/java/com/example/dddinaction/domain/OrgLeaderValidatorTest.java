package com.example.dddinaction.domain;

import com.example.dddinaction.application.organization.CreateOrgRequest;
import com.example.dddinaction.common.exception.BusinessException;
import com.example.dddinaction.domain.organization.emp.EmpRepository;
import com.example.dddinaction.domain.organization.org.validator.OrgLeaderValidator;
import com.example.dddinaction.domain.organization.org.OrgStatus;
import com.example.dddinaction.domain.organization.org.Organization;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrgLeaderValidatorTest {

    @Mock
    private EmpRepository empRepository;

    @InjectMocks
    private OrgLeaderValidator orgLeaderValidator;


    @Test
    public void should_throw_exception_when_org_leader_is_not_a_regular_or_probation_employee() {
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        when(empRepository.existsByIdAndStatus(anyLong(), anyLong(), any())).thenReturn(false);
        BusinessException exception = assertThrows(BusinessException.class, () -> orgLeaderValidator.verify(createOrgRequest.getTenant(), createOrgRequest.getLeader()));
        assertEquals("组织负责人(id='" + createOrgRequest.getLeader() + "')不是在职员工！", exception.getMessage());

    }

    @Test
    public void should_do_nothing_when_org_leader_is_null() {
        CreateOrgRequest createOrgRequest = buildCreateOrgRequest();
        createOrgRequest.setLeader(null);
        orgLeaderValidator.verify(createOrgRequest.getTenant(), createOrgRequest.getLeader());
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

}
