package com.mindex.challenge.service;

import java.util.List;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.data.ReportingStructure;

public interface EmployeeService {
    Employee create(Employee employee);
    Employee read(String id);
    Employee update(Employee employee);

    /**
     * Reads the {@link ReportingStructure} object for the employeeId
     * @param  employeeId the employee id for which to retrieve the reporting structure
     * @return the {@link ReportingStructure} value for the employee
     */
    ReportingStructure readReportingStructure(String employeeId);
}
