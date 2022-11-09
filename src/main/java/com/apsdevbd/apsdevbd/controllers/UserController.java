package com.apsdevbd.apsdevbd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apsdevbd.apsdevbd.entities.AppUser;
import com.apsdevbd.apsdevbd.repositories.UserRepository;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("")
	public ResponseEntity<?> listUsers() {
		if(userRepository.findAll().isEmpty()) {
			return new ResponseEntity<>("{ "+"\"msg\":" +"\"" +"Nenhum usuário salvo" +"\""+" }", HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id) {
		boolean userExists = userRepository.existsById(id);
		if (userExists) {
			return new ResponseEntity<AppUser>(userRepository.findById(id).get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("{ "+"\"msg\":" +"\"" +"Usuário não encontrado" +"\""+" }", HttpStatus.NOT_FOUND);			
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> saveUser(@RequestBody AppUser user) {
		try {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			AppUser savedUser = userRepository.save(user);
			return new ResponseEntity<>("{"+"\"id\":" +savedUser.getId() +"}", HttpStatus.CREATED);			
		} catch (Exception err) {
			return new ResponseEntity<>("error: " + err.getCause().getStackTrace()[0], HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("")
	public ResponseEntity<?> updateUser(@RequestBody AppUser user) {
		try {
			boolean userExists = userRepository.existsById(user.getId());
			if (userExists) {
				user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
				userRepository.saveAndFlush(user);
				return new ResponseEntity<>("{"+"\"id\":" +user.getId() +"}", HttpStatus.OK);	
			}
			return new ResponseEntity<>("{ "+"\"msg\":" +"\"" +"Usuário não encontrado" +"\""+" }", HttpStatus.NOT_FOUND);				
		} catch (Exception err) {
			return new ResponseEntity<>("error: " + err.getCause().getStackTrace()[0], HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		boolean userExists = userRepository.existsById(id);
		if (userExists) {
			userRepository.deleteById(id);
			return new ResponseEntity<>("{"+"\"id\":" +id +"}", HttpStatus.OK);	
		}
		return new ResponseEntity<>("{ "+"\"msg\":" +"\"" +"Usuário não encontrado" +"\""+" }", HttpStatus.NOT_FOUND);	
	}
}
