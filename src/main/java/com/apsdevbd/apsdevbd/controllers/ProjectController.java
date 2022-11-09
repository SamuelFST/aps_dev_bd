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

import com.apsdevbd.apsdevbd.entities.Project;
import com.apsdevbd.apsdevbd.repositories.ProjectRepository;

@RestController
@RequestMapping(value = "/projects", produces = "application/json")
public class ProjectController {
	@Autowired
	private ProjectRepository projectRepository;
	
	@GetMapping("")
	public ResponseEntity<?> listProjects() {
		if(projectRepository.findAll().isEmpty()) {
			return new ResponseEntity<>("{ "+"\"msg\":" +"\"" +"Nenhum projeto salvo" +"\""+" }", HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(projectRepository.findAll(), HttpStatus.OK);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getProject(@PathVariable Long id) {
		boolean projectExists = projectRepository.existsById(id);
		if (projectExists) {
			return new ResponseEntity<Project>(projectRepository.findById(id).get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("{ "+"\"msg\":" +"\"" +"Projeto não encontrado" +"\""+" }", HttpStatus.NOT_FOUND);			
		}
	}

	@PostMapping("")
	public ResponseEntity<?> saveProject(@RequestBody Project project) {
		try {
			Project savedProject = projectRepository.save(project);
			return new ResponseEntity<>("{"+"\"id\":" +savedProject.getId() +"}", HttpStatus.CREATED);			
		} catch (Exception err) {
			return new ResponseEntity<>("error: " + err.getCause().getStackTrace()[0], HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("")
	public ResponseEntity<?> updateProject(@RequestBody Project project) {
		try {
			boolean projectExists = projectRepository.existsById(project.getId());
			if (projectExists) {
				projectRepository.saveAndFlush(project);
				return new ResponseEntity<>("{"+"\"id\":" +project.getId() +"}", HttpStatus.OK);	
			}
			return new ResponseEntity<>("{ "+"\"msg\":" +"\"" +"Projeto não encontrado" +"\""+" }", HttpStatus.NOT_FOUND);				
		} catch (Exception err) {
			return new ResponseEntity<>("error: " + err.getCause().getStackTrace()[0], HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProject(@PathVariable Long id) {
		boolean projectExists = projectRepository.existsById(id);
		if (projectExists) {
			projectRepository.deleteById(id);
			return new ResponseEntity<>("{"+"\"id\":" +id +"}", HttpStatus.OK);	
		}
		return new ResponseEntity<>("{ "+"\"msg\":" +"\"" +"Projeto não encontrado" +"\""+" }", HttpStatus.NOT_FOUND);	
	}
}
