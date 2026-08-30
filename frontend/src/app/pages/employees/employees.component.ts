import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EmployeeService } from '../../core/services/employee.service';


@Component({
  selector: 'app-employees',

  standalone: true,

  imports: [
    CommonModule
  ],

  templateUrl: './employees.component.html',

  styleUrl: './employees.component.css'
})
export class EmployeesComponent {

  private employeeService =
    inject(EmployeeService);


  employees: any[] = [];

  totalElements = 0;

  loading = false;

  errorMessage = '';


  ngOnInit(): void {

    this.loadEmployees();

  }


  loadEmployees(): void {

    this.loading = true;


    this.employeeService
      .getEmployees(0, 20)
      .subscribe({

        next: (response: any) => {

          console.log(
            'FULL API RESPONSE:',
            response
          );


          this.employees =
            response.content ?? [];


          this.totalElements =
            response.totalElements ?? 0;


          console.log(
            'EMPLOYEES ARRAY:',
            this.employees
          );


          console.log(
            'EMPLOYEE COUNT:',
            this.employees.length
          );


          this.loading = false;

        },


        error: (error: any) => {

          console.error(
            'API ERROR:',
            error
          );


          this.errorMessage =
            'Unable to load employees.';


          this.loading = false;

        }

      });

  }

}