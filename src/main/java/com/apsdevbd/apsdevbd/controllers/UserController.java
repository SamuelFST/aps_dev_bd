package com.apsdevbd.apsdevbd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping("/")
	public ResponseEntity<?> listUsers() {
		
		if(userRepository.findAll().isEmpty()) {
			return new ResponseEntity<>("No saved users", HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
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
}
