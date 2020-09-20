package com.mindex.challenge.service.impl;

import java.time.LocalDate;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationUrl;
    private String compensationReadUrl;

    private Compensation compensation;

    @Autowired
    private CompensationService compService;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationReadUrl = "http://localhost:" + port + "/compensation/employee/{id}";
        compensationUrl = "http://localhost:" + port + "/compensation/employee/{id}";
        compensation = new Compensation();
        compensation.setSalary(1456.2f);
        compensation.setCompensationDate(LocalDate.parse("2016-09-09"));
    }

    @Test
    public void testCompensationUpdateRead() {
        // Create employee
        Employee createdEmployee = employeeService.create(new Employee());

        // Create compensation
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Compensation createdCompensation = restTemplate.exchange(compensationUrl, HttpMethod.PUT,
          new HttpEntity<>(compensation, headers), Compensation.class,
          createdEmployee.getEmployeeId()).getBody();
        assertEquals(" Employee id: ", createdEmployee.getEmployeeId(),
          createdCompensation.getEmployee().getEmployeeId());
        checkCompensationSimilarity(compensation, createdCompensation);

        // Read compensation
        Compensation readCompensation = restTemplate.getForEntity(compensationReadUrl, Compensation.class,
          createdEmployee.getEmployeeId()).getBody();
        checkCompensationSimilarity(compensation, readCompensation);
    }

    @Test
    public void testCompensationCreateForNonExistentEmployee() {
        // Create compensation
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity response = restTemplate.exchange(compensationUrl, HttpMethod.PUT,
          new HttpEntity<>(compensation, headers), Compensation.class, "notPresentEmployeeId");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    public static void checkCompensationSimilarity(Compensation initial, Compensation returned) {
        assertEquals("Salary: ", initial.getSalary(), returned.getSalary());
        assertEquals("Date: ", initial.getCompensationDate(), returned.getCompensationDate());
    }

}
