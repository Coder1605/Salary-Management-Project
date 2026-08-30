package com.salary.management.dto;

import java.math.BigDecimal;

public final class AnalyticsDtos {

    private AnalyticsDtos() {
    }

    public record Summary(
            long totalEmployees,
            BigDecimal averageSalary,
            BigDecimal minimumSalary,
            BigDecimal maximumSalary
    ) {
    }

    public record CountryStatistics(
            String country,
            long employeeCount,
            BigDecimal averageSalary
    ) {
    }

    public record DepartmentStatistics(
            String department,
            long employeeCount,
            BigDecimal averageSalary
    ) {
    }
}