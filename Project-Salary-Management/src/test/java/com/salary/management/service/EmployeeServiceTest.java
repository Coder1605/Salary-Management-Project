package com.salary.management.service;

import com.salary.management.dto.EmployeeRequest;
import com.salary.management.dto.EmployeeResponse;

import com.salary.management.entity.Employee;
import com.salary.management.entity.Salary;
import com.salary.management.exception.EmployeeNotFoundException;
import com.salary.management.repository.EmployeeRepository;
import com.salary.management.repository.SalaryRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private SalaryRepository salaryRepository;

    @InjectMocks
    private EmployeeService employeeService;


 

    @Test
    void shouldCreateEmployee() {

        EmployeeRequest request = new EmployeeRequest(
                "Kiran",
                "Test",
                "kiran.test@acme.com",
                "India",
                "Engineering",
                "Software Engineer"
        );

        Employee employee = new Employee();


        employee.setEmployeeCode("EMP00001");
        employee.setFirstName("Kiran");
        employee.setLastName("Test");
        employee.setEmail("kiran.test@acme.com");
        employee.setCountry("India");
        employee.setDepartment("Engineering");
        employee.setJobTitle("Software Engineer");

        when(employeeRepository.count())
                .thenReturn(0L);

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(employee);

        when(salaryRepository
                .findTopByEmployeeIdOrderByEffectiveDateDesc(1L))
                .thenReturn(Optional.empty());


        EmployeeResponse response =
                employeeService.createEmployee(request);


        assertNotNull(response);

        assertEquals(
                1L,
                response.id()
        );

        assertEquals(
                "EMP00001",
                response.employeeCode()
        );

        assertEquals(
                "Kiran",
                response.firstName()
        );

        assertEquals(
                "Test",
                response.lastName()
        );

        assertEquals(
                "kiran.test@acme.com",
                response.email()
        );

        assertEquals(
                "India",
                response.country()
        );

        assertEquals(
                "Engineering",
                response.department()
        );

        assertEquals(
                "Software Engineer",
                response.jobTitle()
        );

        verify(employeeRepository)
                .count();

        verify(employeeRepository)
                .save(any(Employee.class));
    }


   
    @Test
    void shouldGetEmployee() {

        Employee employee = new Employee();

    
        employee.setEmployeeCode("EMP00001");
        employee.setFirstName("Kiran");
        employee.setLastName("Test");
        employee.setEmail("kiran.test@acme.com");
        employee.setCountry("India");
        employee.setDepartment("Engineering");
        employee.setJobTitle("Software Engineer");


        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(salaryRepository
                .findTopByEmployeeIdOrderByEffectiveDateDesc(1L))
                .thenReturn(Optional.empty());


        EmployeeResponse response =
                employeeService.getEmployee(1L);


        assertNotNull(response);

        assertEquals(
                1L,
                response.id()
        );

        assertEquals(
                "EMP00001",
                response.employeeCode()
        );

        assertEquals(
                "Kiran",
                response.firstName()
        );

        assertEquals(
                "Test",
                response.lastName()
        );

        assertEquals(
                "kiran.test@acme.com",
                response.email()
        );

        assertEquals(
                "India",
                response.country()
        );

        assertEquals(
                "Engineering",
                response.department()
        );

        assertEquals(
                "Software Engineer",
                response.jobTitle()
        );


        verify(employeeRepository)
                .findById(1L);
    }


    // =========================================================
    // TEST 3: EMPLOYEE NOT FOUND
    // =========================================================

    @Test
    void shouldThrowExceptionWhenEmployeeDoesNotExist() {

        when(employeeRepository.findById(999L))
                .thenReturn(Optional.empty());


        assertThrows(
                EmployeeNotFoundException.class,
                () -> employeeService.getEmployee(999L)
        );


        verify(employeeRepository)
                .findById(999L);

        verifyNoInteractions(salaryRepository);
    }


    // =========================================================
    // TEST 4: SEARCH / PAGINATION
    // =========================================================

    @Test
    void shouldSearchEmployeesWithPagination() {

        Employee employee1 = new Employee();

  //      employee1.setId(1L);
        employee1.setEmployeeCode("EMP00001");
        employee1.setFirstName("Kiran");
        employee1.setLastName("Test");
        employee1.setEmail("kiran@acme.com");
        employee1.setCountry("India");
        employee1.setDepartment("Engineering");
        employee1.setJobTitle("Software Engineer");


        Employee employee2 = new Employee();

   //     employee2.setId(2L);
        employee2.setEmployeeCode("EMP00002");
        employee2.setFirstName("Rahul");
        employee2.setLastName("Sharma");
        employee2.setEmail("rahul@acme.com");
        employee2.setCountry("India");
        employee2.setDepartment("Finance");
        employee2.setJobTitle("Analyst");


        Page<Employee> employeePage =
                new PageImpl<>(
                        List.of(
                                employee1,
                                employee2
                        ),
                        PageRequest.of(0, 20),
                        2
                );


        when(employeeRepository.searchEmployees(
                "Kiran",
                "India",
                "Engineering",
                PageRequest.of(0, 20)
        )).thenReturn(employeePage);


        when(salaryRepository
                .findTopByEmployeeIdOrderByEffectiveDateDesc(1L))
                .thenReturn(Optional.empty());

        when(salaryRepository
                .findTopByEmployeeIdOrderByEffectiveDateDesc(2L))
                .thenReturn(Optional.empty());


        Page<EmployeeResponse> result =
                employeeService.searchEmployees(
                        "Kiran",
                        "India",
                        "Engineering",
                        PageRequest.of(0, 20)
                );


        assertNotNull(result);

        assertEquals(
                2,
                result.getContent().size()
        );

        assertEquals(
                2,
                result.getTotalElements()
        );


        assertEquals(
                "Kiran",
                result.getContent()
                        .get(0)
                        .firstName()
        );

        assertEquals(
                "Rahul",
                result.getContent()
                        .get(1)
                        .firstName()
        );


        verify(employeeRepository)
                .searchEmployees(
                        "Kiran",
                        "India",
                        "Engineering",
                        PageRequest.of(0, 20)
                );
    }


   
    @Test
    void shouldSearchEmployeesWithoutFilters() {

        Employee employee = new Employee();

        employee.setEmployeeCode("EMP00001");
        employee.setFirstName("Kiran");
        employee.setLastName("Test");
        employee.setEmail("kiran@acme.com");
        employee.setCountry("India");
        employee.setDepartment("Engineering");
        employee.setJobTitle("Software Engineer");


        Page<Employee> page =
                new PageImpl<>(
                        List.of(employee),
                        PageRequest.of(0, 20),
                        1
                );


        when(employeeRepository.searchEmployees(
                null,
                null,
                null,
                PageRequest.of(0, 20)
        )).thenReturn(page);


        when(salaryRepository
                .findTopByEmployeeIdOrderByEffectiveDateDesc(1L))
                .thenReturn(Optional.empty());


        Page<EmployeeResponse> result =
                employeeService.searchEmployees(
                        null,
                        null,
                        null,
                        PageRequest.of(0, 20)
                );


        assertNotNull(result);

        assertEquals(
                1,
                result.getContent().size()
        );

        assertEquals(
                "Kiran",
                result.getContent()
                        .get(0)
                        .firstName()
        );
    }


   
    @Test
    void shouldGetEmployeeWithSalary() {

        Employee employee = new Employee();

  
        employee.setEmployeeCode("EMP00001");
        employee.setFirstName("Kiran");
        employee.setLastName("Test");
        employee.setEmail("kiran@acme.com");
        employee.setCountry("India");
        employee.setDepartment("Engineering");
        employee.setJobTitle("Software Engineer");


        Salary salary = new Salary();

        salary.setAnnualSalary(
                new BigDecimal("800000")
        );

        salary.setCurrency("INR");

        salary.setEffectiveDate(
                LocalDate.of(2026, 1, 1)
        );

        salary.setEmployee(employee);


        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(salaryRepository
                .findTopByEmployeeIdOrderByEffectiveDateDesc(1L))
                .thenReturn(Optional.of(salary));


        EmployeeResponse response =
                employeeService.getEmployee(1L);


        assertNotNull(response);

        assertEquals(
                new BigDecimal("800000"),
                response.annualSalary()
        );

        assertEquals(
                "INR",
                response.currency()
        );

        assertEquals(
                LocalDate.of(2026, 1, 1),
                response.effectiveDate()
        );
    }
}