package com.example.dddinaction.application.employee;

public interface EmployeeService {
    EmpResponse addEmployee(CreateEmpRequest request, Long userId);

    EmpResponse updateEmployee(UpdateEmpRequest request, Long userId);

    EmpResponse findById(Long tenantId, String id);
}
