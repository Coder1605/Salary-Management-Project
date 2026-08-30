package com.salary.management.service;

import com.salary.management.dto.AnalyticsDtos;
import com.salary.management.entity.Salary;
import com.salary.management.repository.EmployeeRepository;
import com.salary.management.repository.SalaryRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private SalaryRepository salaryRepository;

    @InjectMocks
    private AnalyticsService analyticsService;


   

    @Test
    void shouldCalculateSalarySummary() {

      
        when(employeeRepository.count())
                .thenReturn(3L);


     
        Salary salary1 = new Salary();
        salary1.setAnnualSalary(
                new BigDecimal("600000")
        );


      
        Salary salary2 = new Salary();
        salary2.setAnnualSalary(
                new BigDecimal("800000")
        );


     
        Salary salary3 = new Salary();
        salary3.setAnnualSalary(
                new BigDecimal("1000000")
        );


        when(salaryRepository.findAll())
                .thenReturn(
                        List.of(
                                salary1,
                                salary2,
                                salary3
                        )
                );


        // Call service
        AnalyticsDtos.Summary summary =
                analyticsService.getSummary();


        // Assertions

        assertNotNull(summary);


        assertEquals(
                3L,
                summary.totalEmployees()
        );


        // (600000 + 800000 + 1000000) / 3
        // = 800000
        assertEquals(
                new BigDecimal("800000.00"),
                summary.averageSalary()
        );


        assertEquals(
                new BigDecimal("600000"),
                summary.minimumSalary()
        );


        assertEquals(
                new BigDecimal("1000000"),
                summary.maximumSalary()
        );


        // Verify repository calls

        verify(employeeRepository)
                .count();

        verify(salaryRepository)
                .findAll();
    }


    // =========================================================
    // TEST 2: NO EMPLOYEES
    // =========================================================

    @Test
    void shouldReturnZeroSummaryWhenNoEmployeesExist() {

        when(employeeRepository.count())
                .thenReturn(0L);


        AnalyticsDtos.Summary summary =
                analyticsService.getSummary();


        assertNotNull(summary);


        assertEquals(
                0L,
                summary.totalEmployees()
        );


        assertEquals(
                BigDecimal.ZERO,
                summary.averageSalary()
        );


        assertEquals(
                BigDecimal.ZERO,
                summary.minimumSalary()
        );


        assertEquals(
                BigDecimal.ZERO,
                summary.maximumSalary()
        );


        // Salary repository should not be called
        verify(employeeRepository)
                .count();

        verifyNoInteractions(salaryRepository);
    }


    // =========================================================
    // TEST 3: ONE SALARY
    // =========================================================

    @Test
    void shouldCalculateSummaryForSingleSalary() {

        when(employeeRepository.count())
                .thenReturn(1L);


        Salary salary = new Salary();

        salary.setAnnualSalary(
                new BigDecimal("750000")
        );


        when(salaryRepository.findAll())
                .thenReturn(
                        List.of(salary)
                );


        AnalyticsDtos.Summary summary =
                analyticsService.getSummary();


        assertNotNull(summary);


        assertEquals(
                1L,
                summary.totalEmployees()
        );


        assertEquals(
                new BigDecimal("750000.00"),
                summary.averageSalary()
        );


        assertEquals(
                new BigDecimal("750000"),
                summary.minimumSalary()
        );


        assertEquals(
                new BigDecimal("750000"),
                summary.maximumSalary()
        );
    }


   
    @Test
    void shouldRoundAverageSalaryToTwoDecimalPlaces() {

        when(employeeRepository.count())
                .thenReturn(3L);


        Salary salary1 = new Salary();

        salary1.setAnnualSalary(
                new BigDecimal("100")
        );


        Salary salary2 = new Salary();

        salary2.setAnnualSalary(
                new BigDecimal("100")
        );


        Salary salary3 = new Salary();

        salary3.setAnnualSalary(
                new BigDecimal("101")
        );


        when(salaryRepository.findAll())
                .thenReturn(
                        List.of(
                                salary1,
                                salary2,
                                salary3
                        )
                );


        AnalyticsDtos.Summary summary =
                analyticsService.getSummary();


       

        assertEquals(
                new BigDecimal("100.33"),
                summary.averageSalary()
        );
    }
}