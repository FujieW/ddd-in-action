package com.example.dddinaction.domain.organization;

public interface EmpRepository {
    boolean existsByIdAndStatus(Long tenant, Long leader, EmpStatus... empStatus);
}
