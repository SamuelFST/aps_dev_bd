package com.apsdevbd.apsdevbd.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "projects")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "start_date", nullable = false)
	private Date startDate;
	@Column(name = "deadline", nullable = false)
	private Date deadline;
	@Column(name = "is_completed", nullable = false)
	private Boolean is_completed;
	
	public Project() {
	}

	public Project(Long id, String name, Date startDate, Date deadline, Boolean is_completed) {
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.deadline = deadline;
		this.is_completed = is_completed;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Boolean getIs_completed() {
		return is_completed;
	}

	public void setIs_completed(Boolean is_completed) {
		this.is_completed = is_completed;
	}
	
}
