package com.example.dddinaction.domain.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgBuilderFactory {
    @Autowired
    private OrgCommonValidator orgCommonValidator;

    @Autowired
    private OrgLeaderValidator orgLeaderValidator;

    @Autowired
    private OrgNameValidator orgNameValidator;

    @Autowired
    private OrgSuperiorValidator orgSuperiorValidator;

    @Autowired
    private OrgTypeValidator orgTypeValidator;

    public OrgBuilder builder() {
        return new OrgBuilder(orgCommonValidator, orgLeaderValidator, orgNameValidator, orgSuperiorValidator, orgTypeValidator);
    }
}
