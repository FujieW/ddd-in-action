package com.example.dddinaction.domain.organization;

public interface OrgTypeRepository {

    boolean existsByCodeAndStatus(Long tenant, String orgType, OrgStatus orgStatus);
}
