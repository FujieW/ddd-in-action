package com.example.dddinaction.application.employee;

import com.example.dddinaction.domain.organization.emp.Skill;
import lombok.Data;

import java.util.List;

@Data
public class UpdateEmpRequest {
    private Long id;
    private Long tenantId;
    private String idNum;
    private String name;
    private Long orgId;

    private List<Skill> skillList;

    public boolean isSkillPresent(Long skillTypeId) {
        return skillList.stream().anyMatch(skill -> skill.getSkillTypeId().equals(skillTypeId));
    }
}
