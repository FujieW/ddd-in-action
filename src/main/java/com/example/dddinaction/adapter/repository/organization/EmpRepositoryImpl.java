package com.example.dddinaction.adapter.repository.organization;

import com.example.dddinaction.domain.organization.emp.EmpRepository;
import com.example.dddinaction.domain.organization.emp.EmpStatus;
import org.springframework.stereotype.Component;

@Component
public class EmpRepositoryImpl implements EmpRepository {

    public boolean existsByIdAndStatus(Long tenant, Long leader, EmpStatus ... empStatus) {
        return false;
    }
}
