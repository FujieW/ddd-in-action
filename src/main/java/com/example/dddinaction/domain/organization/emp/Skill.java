package com.example.dddinaction.domain.organization.emp;

import com.example.dddinaction.common.framework.domain.AuditableEntity;
import lombok.Getter;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.time.LocalDateTime;

@Getter
public class Skill extends AuditableEntity {
    private Long ID;
    private final Long tenantId;
    private final Long skillTypeId;
    SkillLevel skillLevel;
    private int duration;

    Skill(Long tenantId, Long skillTypeId, LocalDateTime createdAt, Long createdBy) {
        super(createdAt, createdBy);
        this.tenantId = tenantId;
        this.skillTypeId = skillTypeId;
    }

    void setSkillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }

    void setDuration(int duration) {
        this.duration = duration;
    }
}
