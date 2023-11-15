package com.example.dddinaction.adapter.repository.organization;

import com.example.dddinaction.domain.organization.emp.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class EmpRepositoryImpl implements EmpRepository {

    public boolean existsByIdAndStatus(Long tenant, Long empId, EmpStatus ... empStatus) {
        return false;
    }

    @Override
    public void save(Employee employee) {
        saveEmp(employee);
        employee.getSkills().forEach(this::saveSkill);
    }

    @Override
    public Optional<Employee> findById(Long tenantId, String idNum) {
        RebuiltEmp emp = new RebuiltEmp(1L, LocalDateTime.now(), 1L);

        return Optional.of(emp);
    }

    private void saveSkill(Skill skill) {
        switch (skill.getChangingStatus()) {
            case NEW:
                insertSkillRecord(skill);
                break;
            case UPDATED:
                updateSkillRecord(skill);
                break;
            case DELETED:
                deleteSkillRecord(skill);
                break;
        }
    }

    private void deleteSkillRecord(Skill skill) {

    }

    private void updateSkillRecord(Skill skill) {

    }

    private void insertSkillRecord(Skill skill) {

    }

    private void saveEmp(Employee employee) {
        switch (employee.getChangingStatus()) {
            case NEW:
                insertEmpRecord(employee);
                break;
            case UPDATED:
                updateEmpRecord(employee);
                break;
        }
    }

    private void updateEmpRecord(Employee employee) {

    }

    private void insertEmpRecord(Employee employee) {

    }
}
