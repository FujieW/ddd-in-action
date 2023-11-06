package com.example.dddinaction.application.organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CreateOrgRequest {
    private Long tenant;
    private Long superior;
    private String orgType;
    private Long leader;
    private String name;
}
