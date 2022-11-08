package com.apsdevbd.apsdevbd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import com.apsdevbd.apsdevbd.entities.AppUser;
import com.apsdevbd.apsdevbd.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AppUser searchUser = userRepository.findByEmail(email);
		
		if (searchUser == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		UserDetails user = User.withUsername(searchUser.getEmail()).password(searchUser.getPassword()).authorities("USER").build();
		return user;
	}

}
