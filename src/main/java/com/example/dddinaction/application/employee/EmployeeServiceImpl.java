package com.example.dddinaction.application.employee;

import com.example.dddinaction.domain.organization.emp.EmpHandler;
import com.example.dddinaction.domain.organization.emp.EmpRepository;
import com.example.dddinaction.domain.organization.emp.EmpStatus;
import com.example.dddinaction.domain.organization.emp.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmpRepository empRepository;
    private final EmpAssembler empAssembler;
    private final EmpHandler empHandler;

    public EmployeeServiceImpl(EmpRepository empRepository,
                               EmpAssembler empAssembler,
                               EmpHandler empHandler) {
        this.empRepository = empRepository;
        this.empAssembler = empAssembler;
        this.empHandler = empHandler;
    }

    @Override
    public EmpResponse addEmployee(CreateEmpRequest request, Long userId) {
        Employee employee = empAssembler.fromCreateRequest(request, userId);
        empRepository.save(employee);
        return empAssembler.toResponse(employee);
    }

    @Override
    public EmpResponse updateEmployee(UpdateEmpRequest request, Long userId) {
        Employee employee = empRepository.findById(request.getTenantId(), request.getIdNum()).orElseThrow(() -> new RuntimeException("Employee not found"));

        empHandler.update(employee, request, userId);
        empRepository.save(employee);
        return empAssembler.toResponse(employee);
    }
}
