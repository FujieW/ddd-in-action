package com.example.dddinaction.domain.organization.emp;

import com.example.dddinaction.common.framework.domain.ChangingStatus;
import com.mysql.cj.log.Log;

import java.time.LocalDateTime;

public class RebuiltEmp extends Employee {

    public RebuiltEmp(Long tenantId, LocalDateTime createdAt, Long createdBy) {
        super(tenantId, createdAt, createdBy);
        this.changingStatus = ChangingStatus.UNCHANGED;
    }

    RebuiltEmp resetOrgId(Long orgId) {
        this.orgId = orgId;
        return this;
    }

    RebuiltEmp resetId(Long id) {
        this.id = id;
        return this;
    }

    RebuiltEmp resetName(String name) {
        this.name = name;
        return this;
    }

    RebuiltEmp resetIdNum(String idNum) {
        this.idNum = idNum;
        return this;
    }

    RebuiltEmp resetStatus(EmpStatus status) {
        this.status = status;
        return this;
    }

    public RebuiltEmp reAddSkill(Long tenantId, Long skillTypeId, SkillLevel skillLevel, int duration, Long userId) {
        RebuiltSkill rebuiltSkill = new RebuiltSkill(tenantId, skillTypeId, LocalDateTime.now(), userId);
        rebuiltSkill.setSkillLevel(skillLevel);
        rebuiltSkill.setDuration(duration);
        skills.add(rebuiltSkill);
        return this;
    }


}
