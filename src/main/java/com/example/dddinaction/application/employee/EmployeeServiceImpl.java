package com.example.dddinaction.application.employee;

import com.example.dddinaction.domain.organization.emp.EmpRepository;
import com.example.dddinaction.domain.organization.emp.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmpRepository empRepository;
    private final EmpAssembler empAssembler;

    public EmployeeServiceImpl(EmpRepository empRepository,
                               EmpAssembler empAssembler) {
        this.empRepository = empRepository;
        this.empAssembler = empAssembler;
    }

    @Override
    public EmpResponse addEmployee(CreateEmpRequest request, Long userId) {
        Employee employee = empAssembler.fromCreateRequest(request, userId);
        empRepository.save(employee);
        return empAssembler.toResponse(employee);
    }
}
