package com.example.dddinaction.domain.organization.emp;

import java.time.LocalDateTime;

public class RebuiltSkill extends Skill {

    RebuiltSkill(Long tenantId, Long skillTypeId, LocalDateTime createdAt, Long createdBy) {
        super(tenantId, skillTypeId, createdAt, createdBy);
    }


}
