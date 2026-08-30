package com.salary.management.controller;



import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.salary.management.dto.EmployeeRequest;
import com.salary.management.dto.EmployeeResponse;
import com.salary.management.dto.SalaryUpdateRequest;
import com.salary.management.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public Page<EmployeeResponse> searchEmployees(

            @RequestParam(required = false)
            String search,

            @RequestParam(required = false)
            String country,

            @RequestParam(required = false)
            String department,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "20")
            int size) {

        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 100);

        Pageable pageable = PageRequest.of(
                safePage,
                safeSize,
                Sort.by("id").ascending()
        );

        return employeeService.searchEmployees(
                search,
                country,
                department,
                pageable
        );
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployee(
            @PathVariable Long id) {

        return employeeService.getEmployee(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse createEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        return employeeService.createEmployee(request);
    }

    @PutMapping("/{id}/salary")
    public EmployeeResponse updateSalary(
            @PathVariable Long id,
            @Valid @RequestBody SalaryUpdateRequest request) {

        return employeeService.updateSalary(
                id,
                request
        );
    }
}