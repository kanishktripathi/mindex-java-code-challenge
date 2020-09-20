package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @PutMapping("/compensation/employee/{id}")
    public Compensation create(@PathVariable String id, @RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for [{}]", id);
        Employee employee = new Employee();
        employee.setEmployeeId(id);
        compensation.setEmployee(employee);
        return compensationService.update(compensation);
    }

    @GetMapping("/compensation/employee/{id}")
    public Compensation read(@PathVariable String id) {
        LOG.debug("Received compensation create request for employee [{}]", id);
        return compensationService.read(id);
    }

}
