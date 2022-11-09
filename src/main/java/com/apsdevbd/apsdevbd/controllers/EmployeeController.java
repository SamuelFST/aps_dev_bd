package com.apsdevbd.apsdevbd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apsdevbd.apsdevbd.entities.Employee;
import com.apsdevbd.apsdevbd.repositories.EmployeeRepository;

@RestController
@RequestMapping(value = "/employees", produces = "application/json")
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("")
	public ResponseEntity<?> listEmployees() {
		if(employeeRepository.findAll().isEmpty()) {
			return new ResponseEntity<>("{ "+"\"msg\":" +"\"" +"Nenhum funcionário salvo" +"\""+" }", HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getEmployee(@PathVariable Long id) {
		boolean employeeExists = employeeRepository.existsById(id);
		if (employeeExists) {
			return new ResponseEntity<Employee>(employeeRepository.findById(id).get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("{ "+"\"msg\":" +"\"" +"Funcionário não encontrado" +"\""+" }", HttpStatus.NOT_FOUND);			
		}
	}

	@PostMapping("")
	public ResponseEntity<?> saveEmployee(@RequestBody Employee employee) {
		try {
			Employee savedEmployee = employeeRepository.save(employee);
			return new ResponseEntity<>("{"+"\"id\":" +savedEmployee.getId() +"}", HttpStatus.CREATED);			
		} catch (Exception err) {
			return new ResponseEntity<>("error: " + err.getCause().getStackTrace()[0], HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("")
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee) {
		try {
			boolean employeeExists = employeeRepository.existsById(employee.getId());
			if (employeeExists) {
				employeeRepository.saveAndFlush(employee);
				return new ResponseEntity<>("{"+"\"id\":" +employee.getId() +"}", HttpStatus.OK);	
			}
			return new ResponseEntity<>("{ "+"\"msg\":" +"\"" +"Funcionário não encontrado" +"\""+" }", HttpStatus.NOT_FOUND);				
		} catch (Exception err) {
			return new ResponseEntity<>("error: " + err.getCause().getStackTrace()[0], HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
		boolean employeeExists = employeeRepository.existsById(id);
		if (employeeExists) {
			employeeRepository.deleteById(id);
			return new ResponseEntity<>("{"+"\"id\":" +id +"}", HttpStatus.OK);	
		}
		return new ResponseEntity<>("{ "+"\"msg\":" +"\"" +"Funcionário não encontrado" +"\""+" }", HttpStatus.NOT_FOUND);	
	}
}
