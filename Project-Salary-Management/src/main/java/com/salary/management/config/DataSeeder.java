package com.salary.management.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.salary.management.entity.Employee;
import com.salary.management.entity.Salary;
import com.salary.management.repository.EmployeeRepository;
import com.salary.management.repository.SalaryRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Configuration
public class DataSeeder {

    private static final int EMPLOYEE_COUNT = 10_000;

    private static final String[] FIRST_NAMES = {
            "Rahul",
            "Amit",
            "Priya",
            "Neha",
            "Rohit",
            "Sneha",
            "John",
            "David",
            "Michael",
            "Sarah"
    };

    private static final String[] LAST_NAMES = {
            "Sharma",
            "Kumar",
            "Patel",
            "Singh",
            "Verma",
            "Smith",
            "Brown",
            "Wilson",
            "Taylor",
            "Anderson"
    };

    private static final String[] COUNTRIES = {
            "India",
            "USA",
            "UK",
            "Germany",
            "Canada",
            "Australia"
    };

    private static final String[] DEPARTMENTS = {
            "Engineering",
            "Finance",
            "HR",
            "Sales",
            "Marketing",
            "Operations",
            "Product"
    };

    private static final String[] JOB_TITLES = {
            "Software Engineer",
            "Senior Software Engineer",
            "Engineering Manager",
            "Product Manager",
            "HR Manager",
            "Financial Analyst",
            "Sales Executive",
            "Marketing Specialist"
    };

    @Bean
    CommandLineRunner seedDatabase(
            EmployeeRepository employeeRepository,
            SalaryRepository salaryRepository) {

        return args -> {

            if (employeeRepository.count() > 0) {
                return;
            }

            seed(
                    employeeRepository,
                    salaryRepository
            );
        };
    }

    @Transactional
    public void seed(
            EmployeeRepository employeeRepository,
            SalaryRepository salaryRepository) {

        Random random = new Random(42);

        for (int start = 1;
             start <= EMPLOYEE_COUNT;
             start += 100) {

            int end = Math.min(
                    start + 100,
                    EMPLOYEE_COUNT + 1
            );

            List<Employee> employees =
                    new java.util.ArrayList<>();

            List<Salary> salaries =
                    new java.util.ArrayList<>();

            for (int i = start; i < end; i++) {

                Employee employee = new Employee();

                employee.setEmployeeCode(
                        String.format(
                                "EMP%05d",
                                i
                        )
                );

                employee.setFirstName(
                        FIRST_NAMES[
                                random.nextInt(
                                        FIRST_NAMES.length
                                )
                        ]
                );

                employee.setLastName(
                        LAST_NAMES[
                                random.nextInt(
                                        LAST_NAMES.length
                                )
                        ]
                );

                employee.setEmail(
                        "employee"
                                + i
                                + "@acme.example"
                );

                employee.setCountry(
                        COUNTRIES[
                                random.nextInt(
                                        COUNTRIES.length
                                )
                        ]
                );

                employee.setDepartment(
                        DEPARTMENTS[
                                random.nextInt(
                                        DEPARTMENTS.length
                                )
                        ]
                );

                employee.setJobTitle(
                        JOB_TITLES[
                                random.nextInt(
                                        JOB_TITLES.length
                                )
                        ]
                );

                employees.add(employee);
            }

            List<Employee> savedEmployees =
                    employeeRepository.saveAll(employees);

            for (Employee employee : savedEmployees) {

                Salary salary = new Salary();

                salary.setEmployee(employee);

                salary.setCurrency(
                        currencyFor(
                                employee.getCountry()
                        )
                );

                salary.setAnnualSalary(
                        generateSalary(
                                employee.getCountry(),
                                random
                        )
                );

                salary.setEffectiveDate(
                        LocalDate.of(
                                2026,
                                1,
                                1
                        )
                );

                salaries.add(salary);
            }

            salaryRepository.saveAll(salaries);
        }

        System.out.println(
                "Seeded "
                        + EMPLOYEE_COUNT
                        + " employees."
        );
    }

    private String currencyFor(String country) {

        return switch (country) {

            case "India" -> "INR";
            case "USA" -> "USD";
            case "UK" -> "GBP";
            case "Germany" -> "EUR";
            case "Canada" -> "CAD";
            case "Australia" -> "AUD";

            default -> "USD";
        };
    }

    private BigDecimal generateSalary(
            String country,
            Random random) {

        int salary;

        switch (country) {

            case "India":
                salary =
                        400_000
                                + random.nextInt(
                                1_600_001
                        );
                break;

            case "USA":
                salary =
                        50_000
                                + random.nextInt(
                                150_001
                        );
                break;

            case "UK":
                salary =
                        35_000
                                + random.nextInt(
                                100_001
                        );
                break;

            case "Germany":
                salary =
                        40_000
                                + random.nextInt(
                                110_001
                        );
                break;

            case "Canada":
                salary =
                        45_000
                                + random.nextInt(
                                120_001
                        );
                break;

            case "Australia":
                salary =
                        50_000
                                + random.nextInt(
                                130_001
                        );
                break;

            default:
                salary = 50_000;
        }

        return BigDecimal.valueOf(salary);
    }
}