package com.javaconvergence.entity;

import lombok.Data;

@Data
public class Employee {

	public Employee() {
		super();
	}

	public Employee(String empId, String firstName, String lastName, String mobile, String empType) {
		super();
		this.empId = empId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobile = mobile;
		this.empType = empType;
	}

	private String empId;
	private String firstName;
	private String lastName;
	private String mobile;
	private String empType;

}
