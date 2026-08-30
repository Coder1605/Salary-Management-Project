package com.salary.management.controller;

import com.salary.management.dto.EmployeeRequest;
import com.salary.management.dto.EmployeeResponse;
import com.salary.management.dto.SalaryUpdateRequest;
import com.salary.management.service.EmployeeService;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;


    // =========================================================
    // TEST 1: GET ALL / SEARCH EMPLOYEES
    // =========================================================

    @Test
    void shouldSearchEmployees() {

        EmployeeResponse employee = new EmployeeResponse(
                1L,
                "EMP00001",
                "Kiran",
                "Test",
                "kiran.test@acme.com",
                "India",
                "Engineering",
                "Software Engineer",
                new BigDecimal("800000"),
                "INR",
                LocalDate.now()
        );

        Page<EmployeeResponse> page =
                new PageImpl<>(
                        List.of(employee),
                        PageRequest.of(0, 20),
                        1
                );


        when(employeeService.searchEmployees(
                any(),
                any(),
                any(),
                any()
        )).thenReturn(page);


        Page<EmployeeResponse> result =
                employeeController.searchEmployees(
                        null,
                        null,
                        null,
                        0,
                        20
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

        assertEquals(
                "India",
                result.getContent()
                        .get(0)
                        .country()
        );


        verify(employeeService)
                .searchEmployees(
                        isNull(),
                        isNull(),
                        isNull(),
                        any()
                );
    }


    // =========================================================
    // TEST 2: GET EMPLOYEE BY ID
    // =========================================================

    @Test
    void shouldGetEmployeeById() {

        EmployeeResponse employee = new EmployeeResponse(
                1L,
                "EMP00001",
                "Kiran",
                "Test",
                "kiran.test@acme.com",
                "India",
                "Engineering",
                "Software Engineer",
                new BigDecimal("800000"),
                "INR",
                LocalDate.now()
        );


        when(employeeService.getEmployee(1L))
                .thenReturn(employee);


        EmployeeResponse result =
                employeeController.getEmployee(1L);


        assertNotNull(result);

        assertEquals(
                1L,
                result.id()
        );

        assertEquals(
                "Kiran",
                result.firstName()
        );

        assertEquals(
                "Test",
                result.lastName()
        );

        assertEquals(
                "India",
                result.country()
        );


        verify(employeeService)
                .getEmployee(1L);
    }


    // =========================================================
    // TEST 3: CREATE EMPLOYEE
    // =========================================================

    @Test
    void shouldCreateEmployee() {

        EmployeeRequest request =
                new EmployeeRequest(
                        "Kiran",
                        "Test",
                        "kiran.test@acme.com",
                        "India",
                        "Engineering",
                        "Software Engineer"
                );


        EmployeeResponse response =
                new EmployeeResponse(
                        1L,
                        "EMP00001",
                        "Kiran",
                        "Test",
                        "kiran.test@acme.com",
                        "India",
                        "Engineering",
                        "Software Engineer",
                        new BigDecimal("800000"),
                        "INR",
                        LocalDate.now()
                );


        when(employeeService.createEmployee(request))
                .thenReturn(response);


        EmployeeResponse result =
                employeeController.createEmployee(request);


        assertNotNull(result);

        assertEquals(
                "Kiran",
                result.firstName()
        );

        assertEquals(
                "Test",
                result.lastName()
        );

        assertEquals(
                "kiran.test@acme.com",
                result.email()
        );

        assertEquals(
                "Engineering",
                result.department()
        );

        assertEquals(
                "Software Engineer",
                result.jobTitle()
        );


        verify(employeeService)
                .createEmployee(request);
    }


    // =========================================================
    // TEST 4: UPDATE SALARY
    // =========================================================

    @Test
    void shouldUpdateSalary() {

        SalaryUpdateRequest request =
                new SalaryUpdateRequest(
                        new BigDecimal("900000"),
                        "INR",
                        LocalDate.now()
                );


        EmployeeResponse response =
                new EmployeeResponse(
                        1L,
                        "EMP00001",
                        "Kiran",
                        "Test",
                        "kiran.test@acme.com",
                        "India",
                        "Engineering",
                        "Software Engineer",
                        new BigDecimal("900000"),
                        "INR",
                        LocalDate.now()
                );


        when(employeeService.updateSalary(
                eq(1L),
                eq(request)
        )).thenReturn(response);


        EmployeeResponse result =
                employeeController.updateSalary(
                        1L,
                        request
                );


        assertNotNull(result);

        assertEquals(
                new BigDecimal("900000"),
                result.annualSalary()
        );

        assertEquals(
                "INR",
                result.currency()
        );


        verify(employeeService)
                .updateSalary(
                        1L,
                        request
                );
    }


    // =========================================================
    // TEST 5: PAGE AND SIZE SAFETY
    // =========================================================

    @Test
    void shouldHandleInvalidPageAndSize() {

        Page<EmployeeResponse> emptyPage =
                new PageImpl<>(
                        List.of(),
                        PageRequest.of(0, 1),
                        0
                );


        when(employeeService.searchEmployees(
                any(),
                any(),
                any(),
                any()
        )).thenReturn(emptyPage);


        Page<EmployeeResponse> result =
                employeeController.searchEmployees(
                        null,
                        null,
                        null,
                        -5,
                        500
                );


        assertNotNull(result);


        verify(employeeService)
                .searchEmployees(
                        isNull(),
                        isNull(),
                        isNull(),
                        argThat(pageable ->
                                pageable.getPageNumber() == 0
                                &&
                                pageable.getPageSize() == 100
                        )
                );
    }


    // =========================================================
    // TEST 6: SEARCH FILTERS
    // =========================================================

    @Test
    void shouldSearchWithFilters() {

        Page<EmployeeResponse> page =
                new PageImpl<>(
                        List.of()
                );


        when(employeeService.searchEmployees(
                eq("Kiran"),
                eq("India"),
                eq("Engineering"),
                any()
        )).thenReturn(page);


        Page<EmployeeResponse> result =
                employeeController.searchEmployees(
                        "Kiran",
                        "India",
                        "Engineering",
                        0,
                        20
                );


        assertNotNull(result);


        verify(employeeService)
                .searchEmployees(
                        eq("Kiran"),
                        eq("India"),
                        eq("Engineering"),
                        any()
                );
    }
}