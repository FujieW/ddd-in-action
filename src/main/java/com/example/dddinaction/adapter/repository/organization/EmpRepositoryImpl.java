package com.example.dddinaction.adapter.repository.organization;

import com.example.dddinaction.domain.organization.EmpRepository;
import com.example.dddinaction.domain.organization.EmpStatus;
import org.springframework.stereotype.Component;

@Component
public class EmpRepositoryImpl implements EmpRepository {
    public EmpRepositoryImpl() {
    }

    public boolean existsByIdAndStatus(Long tenant, Long leader, EmpStatus ... empStatus) {
        return true;
    }
}
