package com.apsdevbd.apsdevbd.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "age", nullable = false)
	private Integer age;
	@Column(name = "cpf", nullable = false, unique = true)
	private String cpf;
	@Column(name = "phone", nullable = false)
	private String phone;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_employee_type")
	private EmployeeType employeeType;

	public Employee() {
	}

	public Employee(Long id, String name, Integer age, String cpf, String phone, EmployeeType employeeType) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.cpf = cpf;
		this.phone = phone;
		this.employeeType = employeeType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public EmployeeType getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(EmployeeType employeeType) {
		this.employeeType = employeeType;
	}
	
}
