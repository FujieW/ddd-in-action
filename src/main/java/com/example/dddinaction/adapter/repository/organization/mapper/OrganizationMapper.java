package com.example.dddinaction.adapter.repository.organization.mapper;

import com.example.dddinaction.adapter.repository.organization.dataobject.OrganizationDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

@Mapper
public interface OrganizationMapper {
    OrganizationDO findByIdAndStatus(@Param("tenantId") Long tenant, @Param("leader") Long leader, @Param("params") Collection<String> status);
}
