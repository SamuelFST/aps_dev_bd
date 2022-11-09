package com.apsdevbd.apsdevbd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apsdevbd.apsdevbd.entities.EmployeeProjects;
import com.apsdevbd.apsdevbd.repositories.EmployeeProjectsRepository;

@RestController
@RequestMapping(value = "/employee_projects", produces = "application/json")
public class EmployeeProjectsController {
	@Autowired
	private EmployeeProjectsRepository employeeProjectsRepository;
		
	@GetMapping("/projects/{id}")
	public ResponseEntity<?> listEmployeeProjects(@PathVariable Long id) {
		if(employeeProjectsRepository.findAllByEmployeeId(id).isEmpty()) {
			return new ResponseEntity<>("{ "+"\"msg\":" +"\"" +"Nenhum dado salvo" +"\""+" }", HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(employeeProjectsRepository.findAllByEmployeeId(id), HttpStatus.OK);
		}
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<?> listProjectEmployees(@PathVariable Long id) {
		if(employeeProjectsRepository.findAllByProjectId(id).isEmpty()) {
			return new ResponseEntity<>("{ "+"\"msg\":" +"\"" +"Nenhum dado salvo" +"\""+" }", HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(employeeProjectsRepository.findAllByProjectId(id), HttpStatus.OK);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<?> saveEmployeeProject(@RequestBody EmployeeProjects employeeProject) {
		try {
			EmployeeProjects savedEmployeeProject = employeeProjectsRepository.save(employeeProject);
			return new ResponseEntity<>("{"+"\"id\":" +savedEmployeeProject.getId() +"}", HttpStatus.CREATED);			
		} catch (Exception err) {
			return new ResponseEntity<>("error: " + err.getCause().getStackTrace()[0], HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEmployeeProject(@PathVariable Long id) {
		boolean employeeProjectExists = employeeProjectsRepository.existsById(id);
		if (employeeProjectExists) {
			employeeProjectsRepository.deleteById(id);
			return new ResponseEntity<>("{"+"\"id\":" +id +"}", HttpStatus.OK);	
		}
		return new ResponseEntity<>("{ "+"\"msg\":" +"\"" +"Relação não encontrada" +"\""+" }", HttpStatus.NOT_FOUND);	
	}
}
