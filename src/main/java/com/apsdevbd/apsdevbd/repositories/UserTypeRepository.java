package com.apsdevbd.apsdevbd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apsdevbd.apsdevbd.entities.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
	UserType findByName(String name);
}
