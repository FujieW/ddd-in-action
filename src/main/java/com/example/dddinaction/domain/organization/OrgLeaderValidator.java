package com.example.dddinaction.domain.organization;

import com.example.dddinaction.application.organization.CreateOrgRequest;
import com.example.dddinaction.common.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class OrgLeaderValidator extends OrgBaseValidator {

    private EmpRepository empRepository;

    public OrgLeaderValidator(EmpRepository empRepository) {
        this.empRepository = empRepository;
    }

    @Override
    public void verify(CreateOrgRequest request) {
        // 组织负责人可以空缺，如果有的话，的必须是一个在职员工（含试用期）
        if (request.getLeader() != null && !empRepository.existsByIdAndStatus(request.getTenant(), request.getLeader(), EmpStatus.REGULAR, EmpStatus.PROBATION)) {
            throw new BusinessException("组织负责人(id='" + request.getLeader() + "')不是在职员工！");
        }
    }
}
