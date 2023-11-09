package com.example.dddinaction.application.organization;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateOrgRequest {

    private Long orgId;
    private Long superior;
    private String orgType;
    private Long leader;
    private String name;
    private Long tenantId;

}
