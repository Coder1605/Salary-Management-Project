import { Routes } from '@angular/router';

import { EmployeesComponent } from './pages/employees/employees.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { EmployeeFormComponent } from './pages/employee-form/employee-form.component';

export const routes: Routes = [

  {
    path: '',
    redirectTo: 'employees',
    pathMatch: 'full'
  },

  {
    path: 'employees',
    component: EmployeesComponent
  },

  {
    path: 'dashboard',
    component: DashboardComponent
  },

  {
    path: 'employees/new',
    component: EmployeeFormComponent
  },
 
  {
    path: '**',
    redirectTo: 'employees'
  }

];