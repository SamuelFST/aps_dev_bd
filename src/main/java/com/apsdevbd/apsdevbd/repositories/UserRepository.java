package com.apsdevbd.apsdevbd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.apsdevbd.apsdevbd.entities.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {
	AppUser findByEmail(String email);
}
