package com.salary.management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmployeeRequest(

	    @NotBlank(message = "First name is required")
	    String firstName,

	    @NotBlank(message = "Last name is required")
	    String lastName,

	    @NotBlank(message = "Email is required")
	    @Email(message = "Invalid email")
	    String email,

	    @NotBlank(message = "Country is required")
	    String country,

	    @NotBlank(message = "Department is required")
	    String department,

	    @NotBlank(message = "Job title is required")
	    String jobTitle
	) {
	}