package com.example.dddinaction.adapter.repository.organization.dataobject;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "org")
@Data
public class OrganizationDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,  // strategy 设置使用数据库主键自增策略；
            generator = "JDBC") // generator 设置插入完成后，查询最后生成的 ID 填充到该属性中。
    private Long id;

    @Column(nullable = false)
    private Long tenantId;

    @Column(nullable = false)
    private Long superiorId;

    @Column(nullable = false)
    private String orgTypeCode;

    @Column(nullable = false)
    private Long leaderId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "status_code")
    private String statusCode;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Long createdBy;

    @Column(nullable = false)
    private Date lastUpdatedAt;

    @Column(nullable = false)
    private Long lastUpdatedBy;

    public OrganizationDO setStatusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }
}
