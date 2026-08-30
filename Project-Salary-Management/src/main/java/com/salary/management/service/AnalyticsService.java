package com.salary.management.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salary.management.dto.AnalyticsDtos;
import com.salary.management.repository.EmployeeRepository;
import com.salary.management.repository.SalaryRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class AnalyticsService {

    private final EmployeeRepository employeeRepository;
    private final SalaryRepository salaryRepository;

    public AnalyticsService(
            EmployeeRepository employeeRepository,
            SalaryRepository salaryRepository) {

        this.employeeRepository = employeeRepository;
        this.salaryRepository = salaryRepository;
    }

    @Transactional(readOnly = true)
    public AnalyticsDtos.Summary getSummary() {

        long totalEmployees = employeeRepository.count();

        if (totalEmployees == 0) {
            return new AnalyticsDtos.Summary(
                    0,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO
            );
        }

        var salaries = salaryRepository.findAll();

        BigDecimal total = salaries.stream()
                .map(s -> s.getAnnualSalary())
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );

        BigDecimal average = total
                .divide(
                        BigDecimal.valueOf(salaries.size()),
                        2,
                        RoundingMode.HALF_UP
                );

        BigDecimal minimum = salaries.stream()
                .map(s -> s.getAnnualSalary())
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal maximum = salaries.stream()
                .map(s -> s.getAnnualSalary())
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        return new AnalyticsDtos.Summary(
                totalEmployees,
                average,
                minimum,
                maximum
        );
    }
}