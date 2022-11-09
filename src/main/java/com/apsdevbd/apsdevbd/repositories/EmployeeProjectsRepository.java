package com.apsdevbd.apsdevbd.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apsdevbd.apsdevbd.entities.EmployeeProjects;

public interface EmployeeProjectsRepository extends JpaRepository<EmployeeProjects, Long>{
	List<EmployeeProjects> findAllByEmployeeId(Long id);
	List<EmployeeProjects> findAllByProjectId(Long id);
}
