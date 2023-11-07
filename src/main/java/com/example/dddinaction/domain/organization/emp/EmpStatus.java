package com.example.dddinaction.domain.organization.emp;

import com.example.dddinaction.common.exception.BusinessException;

import java.util.Arrays;

public enum EmpStatus {
    REGULAR("REGULAR", "正式"),
    PROBATION("PROBATION", "试用"),
    TERMINATED("TERMINATED", "终止");

    private final String code;
    private final String desc;

    public static EmpStatus ofCode(String code) {
        return Arrays.stream(values())
                .filter( s -> s.code.equals(code))
                .findAny()
                .orElseThrow(() -> new BusinessException(code + "不是有效的员工状态代码！"));
    }

    EmpStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;

    }

    public String code() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
