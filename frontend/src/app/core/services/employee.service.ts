import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/employees';


  getEmployees(
    page: number,
    size: number
  ): Observable<any> {

    return this.http.get<any>(
      `${this.apiUrl}?page=${page}&size=${size}`
    );
  }


  createEmployee(
    employee: any
  ): Observable<any> {

    return this.http.post<any>(
      this.apiUrl,
      employee
    );
  }

}