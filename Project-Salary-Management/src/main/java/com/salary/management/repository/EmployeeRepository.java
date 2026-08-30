package com.salary.management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salary.management.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("""
            SELECT e
            FROM Employee e
            WHERE
                (:search IS NULL OR
                 LOWER(e.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
                 OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
                 OR LOWER(e.employeeCode) LIKE LOWER(CONCAT('%', :search, '%'))
                 OR LOWER(e.email) LIKE LOWER(CONCAT('%', :search, '%')))
            AND (:country IS NULL OR e.country = :country)
            AND (:department IS NULL OR e.department = :department)
            """)
    Page<Employee> searchEmployees(
            @Param("search") String search,
            @Param("country") String country,
            @Param("department") String department,
            Pageable pageable
    );
}