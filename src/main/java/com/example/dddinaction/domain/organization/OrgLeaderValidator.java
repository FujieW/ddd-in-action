package com.example.dddinaction.domain.organization;

import com.example.dddinaction.common.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class OrgLeaderValidator {

    private EmpRepository empRepository;

    public OrgLeaderValidator(EmpRepository empRepository) {
        this.empRepository = empRepository;
    }

    public void verify(Long tenant, Long leader) {
        // 组织负责人可以空缺，如果有的话，的必须是一个在职员工（含试用期）
        if (leader != null && !empRepository.existsByIdAndStatus(tenant, leader, EmpStatus.REGULAR, EmpStatus.PROBATION)) {
            throw new BusinessException("组织负责人(id='" + leader + "')不是在职员工！");
        }
    }
}
