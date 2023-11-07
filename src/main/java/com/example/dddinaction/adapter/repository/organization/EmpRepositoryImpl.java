package com.example.dddinaction.adapter.repository.organization;

import com.example.dddinaction.domain.organization.EmpRepository;
import com.example.dddinaction.domain.organization.EmpStatus;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class EmpRepositoryImpl implements EmpRepository {

    public boolean existsByIdAndStatus(Long tenant, Long leader, EmpStatus ... empStatus) {
        return false;
    }
}
