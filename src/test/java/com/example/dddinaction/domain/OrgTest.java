package com.example.dddinaction.domain;

import com.alibaba.fastjson.JSONObject;
import com.example.dddinaction.application.organization.CreateOrgRequest;
import com.example.dddinaction.application.organization.OrgResponse;
import com.example.dddinaction.application.organization.OrganizationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrgTest {

    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Spy
    private OrganizationServiceImpl organizationService;

    @Autowired
    public OrgTest(WebApplicationContext context, MockMvc mockMvc) {
        this.context = context;
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void should_return_true_when_call_create_organization_give_right_parameter() {
        Long userId = 1L;
        CreateOrgRequest request = buildCreateOrgRequest();
        OrgResponse response = buildOrgResponse();
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/org/organization/add/")
                            .content(JSONObject.toJSONString(request))
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("userId", String.valueOf(userId)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json(JSONObject.toJSONString(response)))
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Mockito.when(organizationService.addOrg(any(), anyLong())).thenReturn(any());
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
