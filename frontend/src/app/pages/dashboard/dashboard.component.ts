import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { EmployeeService } from '../../core/services/employee.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {

  private employeeService = inject(EmployeeService);

  totalEmployees = 0;
  employeesLoaded = 0;
  totalPayroll = 0;
  averageSalary = 0;

  employees: any[] = [];

  loading = false;
  errorMessage = '';

  ngOnInit(): void {
    this.loadDashboard();
  }

  loadDashboard(): void {

    this.loading = true;

    this.employeeService
      .getEmployees(0, 20)
      .subscribe({

        next: (response: any) => {

          console.log('Dashboard response:', response);

          this.employees = response.content ?? [];

          this.totalEmployees =
            response.totalElements ?? 0;

          this.employeesLoaded =
            this.employees.length;

          this.calculateStatistics();

          this.loading = false;
        },

        error: (error: any) => {

          console.error('Dashboard error:', error);

          this.errorMessage =
            'Unable to load dashboard data.';

          this.loading = false;
        }

      });
  }

  calculateStatistics(): void {

    this.totalPayroll =
      this.employees.reduce(
        (total: number, employee: any) => {

          return total +
            Number(employee.annualSalary ?? 0);

        },
        0
      );

    if (this.employees.length > 0) {

      this.averageSalary =
        this.totalPayroll /
        this.employees.length;

    } else {

      this.averageSalary = 0;

    }
  }

  getCountries(): number {

    const countries =
      this.employees.map(
        (employee: any) =>
          employee.country
      );

    return new Set(countries).size;
  }

}