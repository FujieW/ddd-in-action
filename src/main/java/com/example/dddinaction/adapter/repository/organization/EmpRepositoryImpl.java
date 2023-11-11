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
        insertEmp(employee);
        employee.getSkills().forEach(this::insertSkill);
    }

    @Override
    public Optional<Employee> findById(Long tenantId, String idNum) {
        RebuiltEmp emp = new RebuiltEmp(1L, LocalDateTime.now(), 1L);

        return Optional.of(emp);
    }

    private void insertSkill(Skill skill) {

    }

    private void insertEmp(Employee employee) {


    }
}
