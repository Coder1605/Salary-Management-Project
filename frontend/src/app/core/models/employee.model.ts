export interface Employee {

  id: number;

  employeeCode: string;

  firstName: string;

  lastName: string;

  email: string;

  country: string;

  department: string;

  jobTitle: string;

  annualSalary?: number;

  currency?: string;

  effectiveDate?: string;
}


export interface EmployeePage {

  content: Employee[];

  totalElements: number;

  totalPages: number;

  size: number;

  number: number;

}