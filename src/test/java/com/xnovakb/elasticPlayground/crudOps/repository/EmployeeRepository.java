package com.xnovakb.elasticPlayground.crudOps.repository;

import com.xnovakb.elasticPlayground.crudOps.entity.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends ElasticsearchRepository<Employee, Integer> {
}
