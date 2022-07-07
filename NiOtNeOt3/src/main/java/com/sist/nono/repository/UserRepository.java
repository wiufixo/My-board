package com.sist.nono.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nono.model.Board;
import com.sist.nono.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
}
