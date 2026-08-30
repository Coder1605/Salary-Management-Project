package com.salary.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salary.management.entity.Salary;

import java.util.List;
import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, Long> {

    Optional<Salary> findTopByEmployeeIdOrderByEffectiveDateDesc(Long employeeId);

    @Query("""
            SELECT s
            FROM Salary s
            WHERE s.employee.id = :employeeId
            ORDER BY s.effectiveDate DESC
            """)
    List<Salary> findSalaryHistory(Long employeeId);
}