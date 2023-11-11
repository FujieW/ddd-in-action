package com.example.dddinaction.application.employee;

import com.example.dddinaction.domain.organization.emp.EmpHandler;
import com.example.dddinaction.domain.organization.emp.Employee;
import com.example.dddinaction.domain.organization.org.validator.OrgCommonValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmpAssembler {

    private OrgCommonValidator orgCommonValidator;
    private EmpHandler empHandler;

    public EmpAssembler(OrgCommonValidator orgCommonValidator,
                        EmpHandler empHandler) {
        this.orgCommonValidator = orgCommonValidator;
        this.empHandler = empHandler;
    }

    public Employee fromCreateRequest(CreateEmpRequest request, Long userId) {
        validateCreateRequest(request);
        Employee newEmployee = new Employee(request.getTenantId(), LocalDateTime.now(), userId);
        newEmployee.setNum(empHandler.generateNum());
        newEmployee.setDob(LocalDateTime.now());
        newEmployee.setName(request.getName());
        newEmployee.setOrgId(request.getOrgId());

        request.getSkillList().forEach(skill ->  {
            newEmployee.addSkill(request.getTenantId(), skill.getSkillTypeId(), skill.getSkillLevel(), skill.getDuration(), userId);
        });

        return newEmployee;
    }

    private void validateCreateRequest(CreateEmpRequest request) {
        orgCommonValidator.verify(request.getTenantId());
    }

    public EmpResponse toResponse(Employee employee) {
        return null;
    }
}
