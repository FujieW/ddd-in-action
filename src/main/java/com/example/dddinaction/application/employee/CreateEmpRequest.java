package com.example.dddinaction.application.employee;

import com.example.dddinaction.domain.organization.emp.Skill;
import lombok.Data;

import java.util.List;

@Data
public class CreateEmpRequest {
    private Long tenantId;
    private String idNum;
    private String name;
    private Long orgId;

    private List<Skill> skillList;
}
