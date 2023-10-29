package com.example.dddinaction.domain;

import com.example.dddinaction.adapter.repository.user.UserRepository;
import com.example.dddinaction.application.organization.CreateOrgRequest;
import com.example.dddinaction.application.organization.OrgResponse;
import com.example.dddinaction.common.exception.BusinessException;
import com.example.dddinaction.domain.organization.*;
import com.example.dddinaction.domain.tenant.TenantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrgValidatorTest {

    private OrgValidator orgValidator;

    private List<OrgBaseValidator> validators;

    @Mock
    private OrgSuperiorValidator superiorValidator;

    @Mock
    private OrgNameValidator orgNameValidator;

    @BeforeEach
    public void setUp() {
        validators = new ArrayList<>();
        validators.add(superiorValidator);
        validators.add(orgNameValidator);
        orgValidator = new OrgValidator(validators);
    }

    @Test
    public void should_verify_create_org_request_param() {

        for (OrgBaseValidator validator : validators) {
            doNothing().when(validator).verify(any());
        }
        CreateOrgRequest request = buildCreateOrgRequest();
        orgValidator.validator(request, 1L);

        for (OrgBaseValidator validator : validators) {
            verify(validator).verify(request);
        }
    }

    private CreateOrgRequest buildCreateOrgRequest() {
        CreateOrgRequest org = CreateOrgRequest.builder().name("狗剩集团").orgType("DEVCENT").tenant(1L).superior(1L).leader(1L).build();
        return org;
    }
}
