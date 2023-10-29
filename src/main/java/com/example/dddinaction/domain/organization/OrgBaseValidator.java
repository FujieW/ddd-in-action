package com.example.dddinaction.domain.organization;

import com.example.dddinaction.application.organization.CreateOrgRequest;

public abstract class OrgBaseValidator {
    public abstract void verify(CreateOrgRequest request);
}
