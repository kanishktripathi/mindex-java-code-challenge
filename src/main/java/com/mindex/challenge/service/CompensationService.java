package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.data.ReportingStructure;

public interface CompensationService {

    /**
     * Updates the compensation for the employee present in the {@link Compensation} object. Will throw an error
     * if the employee is not already present
     * @param compensation the compensation object
     * @return the successfully updated {@link Compensation} object
     */
    Compensation update(Compensation compensation);

    /**
     * Reads {@link Compensation} object for the employee present. Will throw an error
     * if the employee is not already present
     * @param employeeId the employeeId
     * @return the  {@link Compensation} object corresponding to the employeeId
     */
    Compensation read(String employeeId);

}
