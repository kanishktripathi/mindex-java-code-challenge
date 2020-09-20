package com.mindex.challenge.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.data.ReportingStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    @Override
    public ReportingStructure readReportingStructure(String employeeId) {
        Set<String> allReports = new HashSet<>();
        Employee employee = read(employeeId);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }
        ReportingStructure reportingStructure = new ReportingStructure(employee);
        Set<String> reportingEmployees = employee.getDirectReports().stream().map(Employee::getEmployeeId)
          .filter(s -> s != null).collect(Collectors.toSet());
        while (!reportingEmployees.isEmpty()) {
            List<String> directEmployees = reportingEmployees.stream().filter(s -> !allReports.contains(s))
              .collect(Collectors.toList());
            if (!directEmployees.isEmpty()) {
                List<Employee> directReports = employeeRepository.findByEmployeeIdIn(directEmployees).stream()
                  .filter(e -> e != null).collect(Collectors.toList());
                directReports.forEach(e -> allReports.add(e.getEmployeeId()));
                Set<String> nextReports = directReports.stream().map(Employee::getDirectReports).flatMap(List::stream)
                  .map(Employee::getEmployeeId)
                  .filter(s -> s != null).collect(Collectors.toSet());
                reportingEmployees = nextReports;
            } else {
                reportingEmployees = Collections.emptySet();
            }
        }
        allReports.remove(employeeId);
        reportingStructure.setNumberOfReports(allReports.size());
        return reportingStructure;
    }
}
