package com.mindex.challenge.service.data;

import com.mindex.challenge.data.Employee;

public class ReportingStructure {

  private Employee employee;

  private int numberOfReports;

  public ReportingStructure() {
  }

  public ReportingStructure(Employee employee) {
    this.employee = employee;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public int getNumberOfReports() {
    return numberOfReports;
  }

  public void setNumberOfReports(int numberOfReports) {
    this.numberOfReports = numberOfReports;
  }
}
