package com.example.dddinaction.domain.tenant;

import com.example.dddinaction.common.exception.BusinessException;

import java.util.Arrays;

public enum TenantStatus {
    EFFECTIVE("EF", "有效"),
    TERMINATED("TE", "终止");

    private final String code;
    private final String desc;

    public static TenantStatus ofCode(String code) {
        return Arrays.stream(values())
                .filter( s -> s.code.equals(code))
                .findAny()
                .orElseThrow(() -> new BusinessException(code + "不是有效的租户状态代码！"));
    }

    TenantStatus(String code, String desc) {
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
