package com.example.dddinaction.adapter.repository.organization.dao;

import com.example.dddinaction.adapter.repository.organization.dataobject.OrganizationDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrganizationDAO extends PagingAndSortingRepository<OrganizationDO, Integer> {

    @Query(value = "select o from OrganizationDO o where o.id = ?1 and o.statusCode = ?2")
    OrganizationDO findByIdAndStatusCode(Long id, String statusCode);
}
