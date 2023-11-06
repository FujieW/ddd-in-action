package com.example.dddinaction.application.organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class OrgResponse {
    private Long tenantId;
    private Long superior;
    private String orgType;
    private Long leader;
    private String name;
}
