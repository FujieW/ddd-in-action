package com.example.dddinaction.domain.organization.emp;

import java.util.Optional;

public interface EmpRepository {
    boolean existsByIdAndStatus(Long tenant, Long empId, EmpStatus... empStatus);

    void save(Employee employee);

    Optional<Employee> findById(Long tenantId, String idNum);
}
