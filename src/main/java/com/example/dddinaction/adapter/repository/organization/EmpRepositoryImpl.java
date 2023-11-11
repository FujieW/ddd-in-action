package com.example.dddinaction.adapter.repository.organization;

import com.example.dddinaction.domain.organization.emp.EmpRepository;
import com.example.dddinaction.domain.organization.emp.EmpStatus;
import com.example.dddinaction.domain.organization.emp.Employee;
import com.example.dddinaction.domain.organization.emp.Skill;
import org.springframework.stereotype.Component;

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
        return Optional.empty();
    }

    private void insertSkill(Skill skill) {

    }

    private void insertEmp(Employee employee) {


    }
}
