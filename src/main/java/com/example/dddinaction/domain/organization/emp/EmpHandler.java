package com.example.dddinaction.domain.organization.emp;

import com.example.dddinaction.application.employee.UpdateEmpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class EmpHandler {
    public String generateNum() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public void update(Employee employee, UpdateEmpRequest request, Long userId) {
        employee.setNum(request.getIdNum());
        employee.toUpdate();

        updateSkills(employee, request, userId);
    }

    private void updateSkills(Employee employee, UpdateEmpRequest request, Long userId) {
        deleteAbsentSKills(employee, request);
        operatePresentSkills(employee, request, userId);
    }

    private void operatePresentSkills(Employee employee, UpdateEmpRequest request, Long userId) {
        request.getSkillList().forEach(skill -> {
            Optional<Skill> skill1 = employee.getSkill(skill.getSkillTypeId());
            if (skill1.isPresent()) {
                employee.updateSKill(skill.getSkillTypeId(), skill.getSkillLevel(), skill.getDuration(), userId);
            } else {
                employee.addSkill(skill.getTenantId(), skill.getSkillTypeId(), skill.getSkillLevel(), skill.getDuration(), userId);
            }
        });
    }

    private void deleteAbsentSKills(Employee employee, UpdateEmpRequest request) {
        employee.getSkills().forEach(skill -> {
            if (!request.isSkillPresent(skill.getSkillTypeId())) {
                employee.deleteSkill(skill.getSkillTypeId());
            }
        });
    }
}
