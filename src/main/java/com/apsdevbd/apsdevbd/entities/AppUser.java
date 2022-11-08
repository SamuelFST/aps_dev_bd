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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "users")
public class AppUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "age", nullable = false)
	private Integer age;
	@Column(name = "cpf", nullable = false, unique = true)
	private String cpf;
	@Column(name = "phone", nullable = false)
	private String phone;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_type_id")
	private UserType userType;

	public AppUser() {
	}

	public AppUser(
		Long id, 
		String email, 
		String password, 
		String name, 
		Integer age, 
		String cpf, 
		String phone,
		UserType userType
	) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.age = age;
		this.cpf = cpf;
		this.phone = phone;
		this.userType = userType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
}
