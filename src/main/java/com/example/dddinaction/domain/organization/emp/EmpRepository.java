package com.example.dddinaction.domain.organization.emp;

public interface EmpRepository {
    boolean existsByIdAndStatus(Long tenant, Long leader, EmpStatus... empStatus);
}
