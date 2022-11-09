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

import com.apsdevbd.apsdevbd.entities.EmployeeType;
import com.apsdevbd.apsdevbd.repositories.EmployeeTypeRepository;

@RestController
@RequestMapping(value = "/employee_types", produces = "application/json")
public class EmployeeTypeController {
	@Autowired
	private EmployeeTypeRepository employeeTypeRepository;
	
	@GetMapping("")
	public ResponseEntity<?> listEmployeeTypes() {
		if(employeeTypeRepository.findAll().isEmpty()) {
			return new ResponseEntity<>("{ "+"\"msg\":" +"\"" +"Nenhum tipo de funcionário salvo" +"\""+" }", HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(employeeTypeRepository.findAll(), HttpStatus.OK);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getEmployeeType(@PathVariable Long id) {
		boolean employeeTypeExists = employeeTypeRepository.existsById(id);
		if (employeeTypeExists) {
			return new ResponseEntity<EmployeeType>(employeeTypeRepository.findById(id).get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("{ "+"\"msg\":" +"\"" +"Tipo de funcionário não encontrado" +"\""+" }", HttpStatus.NOT_FOUND);			
		}
	}
	
	@PostMapping("")
	public ResponseEntity<?> saveEmployeeType(@RequestBody EmployeeType employeeType) {
		try {
			employeeTypeRepository.save(employeeType);
			return new ResponseEntity<>("{"+"\"id\":" +employeeType.getId() +"}", HttpStatus.CREATED);			
		} catch (Exception err) {
			return new ResponseEntity<>("error: " + err.getCause().getStackTrace()[0], HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEmployeeTYpe(@PathVariable Long id) {
		boolean employeeTypeExists = employeeTypeRepository.existsById(id);
		if (employeeTypeExists) {
			employeeTypeRepository.deleteById(id);
			return new ResponseEntity<>("{"+"\"id\":" +id +"}", HttpStatus.OK);	
		}
		return new ResponseEntity<>("{ "+"\"msg\":" +"\"" +"Tipo de funcionário não encontrado" +"\""+" }", HttpStatus.NOT_FOUND);	
	}
	
}
