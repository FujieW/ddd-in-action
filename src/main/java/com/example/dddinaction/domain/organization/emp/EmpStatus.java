package com.example.dddinaction.domain.organization.emp;

import com.example.dddinaction.common.exception.BusinessException;
import lombok.Getter;

import java.util.Arrays;

public enum EmpStatus {
    REGULAR("REGULAR", "正式"),
    PROBATION("PROBATION", "试用"),
    TERMINATED("TERMINATED", "终止");

    @Getter
    private final String code;
    @Getter
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

    public EmpStatus becomeRegular() {
        if (this != PROBATION) {
            throw new BusinessException("试用期员工才能转正");
        }
        return EmpStatus.REGULAR;
    }

    public EmpStatus terminate() {
        if (this != REGULAR) {
            throw new BusinessException("正式员工才能终止");
        }
        return EmpStatus.TERMINATED;
    }

}
