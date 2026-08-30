package com.salary.management.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SalaryUpdateRequest(

        @NotNull(message = "Annual salary is required")
        @DecimalMin(value = "0.01", message = "Salary must be greater than zero")
        BigDecimal annualSalary,

        @NotBlank(message = "Currency is required")
        String currency,

        @NotNull(message = "Effective date is required")
        LocalDate effectiveDate
) {
}