package com.wow.employee.info.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.wow.employee.info.models.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

	@Query(value = "{ 'departmentId' : ?0 }", fields = "?1")
	List<Employee> findAllByDepartmentId(String departId, Document fields);

	@Query(value = "{ 'id' : ?0 }", fields = "?1")
	Employee findByEmployeeId(String empId, Document fields);

}
