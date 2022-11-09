package com.apsdevbd.apsdevbd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apsdevbd.apsdevbd.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
