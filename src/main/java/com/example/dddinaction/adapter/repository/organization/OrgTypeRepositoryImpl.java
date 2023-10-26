package com.example.dddinaction.adapter.repository.organization;

import com.example.dddinaction.domain.organization.OrgStatus;
import org.springframework.stereotype.Component;

@Component
public class OrgTypeRepositoryImpl implements com.example.dddinaction.domain.organization.OrgTypeRepository {
    public boolean existsByCodeAndStatus(Long tenant, String orgType, OrgStatus orgStatus) {
        return false;
    }
}
