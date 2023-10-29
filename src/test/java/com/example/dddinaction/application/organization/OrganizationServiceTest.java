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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class OrganizationServiceTest {

    @Autowired
    private OrganizationServiceImpl organizationService;

    @DisplayName("Test_For_Add_Org")
    @Nested
    class AddOrgInterface {
        @Test
        public void should_add_org_when_given_orgdto_and_operatorId() {
            CreateOrgRequest request = buildCreateOrgRequest();
            OrgResponse response = buildOrgResponse();
            Long userId = 1L;

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
