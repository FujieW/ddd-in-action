package com.example.dddinaction.adapter.controller.organization;

import com.example.dddinaction.application.organization.CreateOrgRequest;
import com.example.dddinaction.application.organization.OrgResponse;
import com.example.dddinaction.application.organization.OrganizationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@RequestMapping("api/org/organization/")
public class OrganizationController {

    private OrganizationServiceImpl organizationService;

    @Autowired
    public OrganizationController(OrganizationServiceImpl organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping("add/")

    public OrgResponse createOrg(@RequestBody CreateOrgRequest createOrgRequest, @RequestParam("userId") Long userId) {
        return organizationService.addOrg(createOrgRequest, userId);
    }

}
