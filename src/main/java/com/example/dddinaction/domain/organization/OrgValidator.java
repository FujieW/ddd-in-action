package com.example.dddinaction.domain.organization;

import com.example.dddinaction.application.organization.CreateOrgRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrgValidator {

    @Autowired
    private List<OrgBaseValidator> validators;

    public OrgValidator(List<OrgBaseValidator> validators) {
        this.validators = validators;
    }

    public void validator(CreateOrgRequest request, Long userId) {
        for (OrgBaseValidator validator : validators) {
            validator.verify(request);
        }
    }
}
