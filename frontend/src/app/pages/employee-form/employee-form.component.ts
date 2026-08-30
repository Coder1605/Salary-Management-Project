import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { Router } from '@angular/router';

import { EmployeeService } from '../../core/services/employee.service';

@Component({
  selector: 'app-employee-form',
  standalone: true,

  imports: [
    CommonModule,
    ReactiveFormsModule
  ],

  templateUrl: './employee-form.component.html',
  styleUrl: './employee-form.component.css'
})
export class EmployeeFormComponent {

  private fb = inject(FormBuilder);

  private employeeService =
    inject(EmployeeService);

  private router = inject(Router);


  submitting = false;

  successMessage = '';

  errorMessage = '';


  employeeForm = this.fb.group({

    firstName: [
      '',
      [
        Validators.required,
        Validators.minLength(2)
      ]
    ],

    lastName: [
      '',
      [
        Validators.required,
        Validators.minLength(2)
      ]
    ],

    email: [
      '',
      [
        Validators.required,
        Validators.email
      ]
    ],

    country: [
      '',
      Validators.required
    ],

    department: [
      '',
      Validators.required
    ],

    jobTitle: [
      '',
      Validators.required
    ],

    annualSalary: [
      0,
      [
        Validators.required,
        Validators.min(1)
      ]
    ],

    currency: [
      'INR',
      Validators.required
    ]

  });


  submit(): void {

    if (this.employeeForm.invalid) {

      this.employeeForm.markAllAsTouched();

      return;

    }


    this.submitting = true;

    this.successMessage = '';

    this.errorMessage = '';


    const employee =
      this.employeeForm.getRawValue();


    this.employeeService
      .createEmployee(employee)
      .subscribe({

        next: (response :any) => {

          console.log(
            'Employee created:',
            response
          );

          this.submitting = false;

          this.successMessage =
            'Employee created successfully!';


          setTimeout(() => {

            this.router.navigate(
              ['/employees']
            );

          }, 1000);

        },


        error: (error:any) => {

          console.error(
            'Create employee error:',
            error
          );

          this.submitting = false;

          this.errorMessage =
            error?.error?.message ??
            'Unable to create employee.';

        }

      });

  }


  cancel(): void {

    this.router.navigate(
      ['/employees']
    );

  }

}