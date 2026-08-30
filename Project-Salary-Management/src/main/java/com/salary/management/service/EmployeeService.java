package com.salary.management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salary.management.dto.EmployeeRequest;
import com.salary.management.dto.EmployeeResponse;
import com.salary.management.dto.SalaryUpdateRequest;
import com.salary.management.entity.Employee;
import com.salary.management.entity.Salary;
import com.salary.management.exception.EmployeeNotFoundException;
import com.salary.management.repository.EmployeeRepository;
import com.salary.management.repository.SalaryRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final SalaryRepository salaryRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            SalaryRepository salaryRepository) {

        this.employeeRepository = employeeRepository;
        this.salaryRepository = salaryRepository;
    }

    @Transactional(readOnly = true)
    public Page<EmployeeResponse> searchEmployees(
            String search,
            String country,
            String department,
            Pageable pageable) {

        return employeeRepository
                .searchEmployees(
                        normalize(search),
                        normalize(country),
                        normalize(department),
                        pageable
                )
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public EmployeeResponse getEmployee(Long id) {

        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(id));

        return toResponse(employee);
    }

    @Transactional
    public EmployeeResponse createEmployee(EmployeeRequest request) {

        Employee employee = new Employee();

        employee.setEmployeeCode(generateEmployeeCode());
        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setEmail(request.email());
        employee.setCountry(request.country());
        employee.setDepartment(request.department());
        employee.setJobTitle(request.jobTitle());

        Employee saved = employeeRepository.save(employee);

        return toResponse(saved);
    }

    @Transactional
    public EmployeeResponse updateSalary(
            Long employeeId,
            SalaryUpdateRequest request) {

        Employee employee = employeeRepository
                .findById(employeeId)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(employeeId));

        Salary salary = new Salary();

        salary.setEmployee(employee);
        salary.setAnnualSalary(request.annualSalary());
        salary.setCurrency(
                request.currency().toUpperCase()
        );
        salary.setEffectiveDate(
                request.effectiveDate()
        );

        salaryRepository.save(salary);

        return toResponse(employee);
    }

    private EmployeeResponse toResponse(Employee employee) {

        Salary salary = salaryRepository
                .findTopByEmployeeIdOrderByEffectiveDateDesc(
                        employee.getId()
                )
                .orElse(null);

        return new EmployeeResponse(
                employee.getId(),
                employee.getEmployeeCode(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getCountry(),
                employee.getDepartment(),
                employee.getJobTitle(),
                salary != null
                        ? salary.getAnnualSalary()
                        : null,
                salary != null
                        ? salary.getCurrency()
                        : null,
                salary != null
                        ? salary.getEffectiveDate()
                        : null
        );
    }

    private String normalize(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }

    private String generateEmployeeCode() {

        long count = employeeRepository.count() + 1;

        return String.format(
                "EMP%05d",
                count
        );
    }
}