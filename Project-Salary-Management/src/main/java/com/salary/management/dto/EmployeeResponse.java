package com.salary.management.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeResponse(
        Long id,
        String employeeCode,
        String firstName,
        String lastName,
        String email,
        String country,
        String department,
        String jobTitle,
        BigDecimal annualSalary,
        String currency,
        LocalDate effectiveDate
) {
}