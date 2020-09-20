package com.mindex.challenge.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;

public class Compensation {

    @Id
    private Employee employee;
    private Float salary;
    private LocalDate compensationDate;

    public Employee getEmployee() {
        return employee;
    }

    public Compensation setEmployee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public Float getSalary() {
        return salary;
    }

    public Compensation setSalary(Float salary) {
        this.salary = salary;
        return this;
    }

    public LocalDate getCompensationDate() {
        return compensationDate;
    }

    public Compensation setCompensationDate(LocalDate compensationDate) {
        this.compensationDate = compensationDate;
        return this;
    }
}
