package com.mindex.challenge.dao;

import java.util.List;
import com.mindex.challenge.data.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    Employee findByEmployeeId(String employeeId);

    /**
     * Find all the employees by employee ids
     * @param employeeIds the list of employee id
     * @return the list of type {@link Employee}
     */
    List<Employee> findByEmployeeIdIn(List<String> employeeIds);
}
