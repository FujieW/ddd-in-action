/*
package com.example.dddinaction.adapter.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.dddinaction.adapter.controller.organization.OrganizationController;
import com.example.dddinaction.application.organization.CreateOrgRequest;
import com.example.dddinaction.application.organization.OrgResponse;
import com.example.dddinaction.application.organization.OrganizationService;
import com.example.dddinaction.application.organization.OrganizationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrganizationControllerTest {
    private final WebApplicationContext context;

    private MockMvc mockMvc;

    private OrganizationService organizationService;

    @Autowired
    public OrganizationControllerTest(WebApplicationContext context, MockMvc mockMvc) {
        this.context = context;
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        organizationService = Mockito.mock(OrganizationServiceImpl.class);
    }

    @Test
    public void should_return_true_when_call_create_organization_give_right_parameter() {
        Long userId = 1L;
        CreateOrgRequest request = buildCreateOrgRequest();
        OrgResponse response = buildOrgResponse();
        Mockito.when(organizationService.addOrg(request, userId)).thenReturn(response);
        try {
            MvcResult  mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/org/organization/add/")
                            .content(JSONObject.toJSONString(request))
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("userId", String.valueOf(userId)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    // .andExpect(MockMvcResultMatchers.content().json(JSONObject.toJSONString(response)))
                    .andReturn();
            String content = mvcResult.getResponse().getContentAsString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private OrgResponse buildOrgResponse() {
        return OrgResponse.builder()
                .tenantId(1L)
                .name("狗剩集团")
                .superior(1L)
                .tenantId(1L)
                .orgType("开发中心")
                .build();

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
}
*/
