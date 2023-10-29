package com.example.dddinaction.adapter.repository.organization.dataobject;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrganizationDO {

    private Long id;

    private Long tenantId;

    private Long superiorId;

    private String orgTypeCode;

    private Long leaderId;

    private String name;

    private String orgStatusCode;

    private LocalDateTime createdAt;

    private Long createdBy;

    private LocalDateTime lastedUpdatedAt;

    private Long lastedUpdatedBy;

}
